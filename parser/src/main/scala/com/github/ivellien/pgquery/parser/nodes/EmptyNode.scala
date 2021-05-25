package com.github.ivellien.pgquery.parser.nodes

case object EmptyNode extends Node {
  override def query: String = ""
}
