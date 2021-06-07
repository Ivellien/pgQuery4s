package com.github.ivellien.pgquery.parser.nodes.values

import com.github.ivellien.pgquery.parser.enums.NodeTag
import com.github.ivellien.pgquery.parser.nodes.Node
import com.typesafe.scalalogging.LazyLogging
import io.circe.{Decoder, HCursor}
import io.circe.generic.extras.Configuration
import com.github.ivellien.pgquery.parser.nodes.Node.circeConfig

abstract class Value extends Node

object Value extends LazyLogging {
  implicit val circeConfig: Configuration = Configuration.default.withDefaults

  implicit val decoder: Decoder[Value] = (c: HCursor) => {
    c.keys match {
      case Some(keys) if keys.nonEmpty =>
        val key: String = keys.head

        NodeTag.withName(key) match {
          case NodeTag.T_Integer  => c.as[NodeInteger]
          case NodeTag.T_String   => c.as[NodeString]
          case NodeTag.T_A_Star   => c.as[A_Star.type]
          case NodeTag.T_ParamRef => c.as[ParamRef]
          case NodeTag.T_A_Const  => c.as[A_Const]
          case _ =>
            logger.error(s"Not a Value type - $key")
            Right(NodeString(""))
        }
      case _ => Right(NodeString(""))
    }
  }
}
