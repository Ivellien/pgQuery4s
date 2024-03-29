package com.github.ivellien.pgquery.parser.enums

object AlterTableType extends Enumeration with EnumerationDecoder {
  val AtAddcolumn: Value = Value("ADD")
  val AtAddcolumnrecurse: Value = Value("")
  val AtAddcolumntoview: Value = Value("")
  val AtColumndefault: Value = Value("")
  val AtDropnotnull: Value = Value("")
  val AtSetnotnull: Value = Value("")
  val AtSetstatistics: Value = Value("")
  val AtSetoptions: Value = Value("")
  val AtResetoptions: Value = Value("")
  val AtSetstorage: Value = Value("")
  val AtDropcolumn: Value = Value("DROP COLUMN")
  val AtDropcolumnrecurse: Value = Value("")
  val AtAddindex: Value = Value("")
  val AtReaddindex: Value = Value("")
  val AtAddconstraint: Value = Value("")
  val AtAddconstraintrecurse: Value = Value("")
  val AtReaddconstraint: Value = Value("")
  val AtAlterconstraint: Value = Value("")
  val AtValidateconstraint: Value = Value("")
  val AtValidateconstraintrecurse: Value = Value("")
  val AtProcessedconstraint: Value = Value("")
  val AtAddindexconstraint: Value = Value("")
  val AtDropconstraint: Value = Value("")
  val AtDropconstraintrecurse: Value = Value("")
  val AtReaddcomment: Value = Value("")
  val AtAltercolumntype: Value = Value("")
  val AtAltercolumngenericoptions: Value = Value("")
  val AtChangeowner: Value = Value("")
  val AtClusteron: Value = Value("")
  val AtDropcluster: Value = Value("")
  val AtSetlogged: Value = Value("")
  val AtSetunlogged: Value = Value("")
  val AtAddoids: Value = Value("")
  val AtAddoidsrecurse: Value = Value("")
  val AtDropoids: Value = Value("")
  val AtSettablespace: Value = Value("")
  val AtSetreloptions: Value = Value("")
  val AtResetreloptions: Value = Value("")
  val AtReplacereloptions: Value = Value("")
  val AtEnabletrig: Value = Value("")
  val AtEnablealwaystrig: Value = Value("")
  val AtEnablereplicatrig: Value = Value("")
  val AtDisabletrig: Value = Value("")
  val AtEnabletrigall: Value = Value("")
  val AtDisabletrigall: Value = Value("")
  val AtEnabletriguser: Value = Value("")
  val AtDisabletriguser: Value = Value("")
  val AtEnablerule: Value = Value("")
  val AtEnablealwaysrule: Value = Value("")
  val AtEnablereplicarule: Value = Value("")
  val AtDisablerule: Value = Value("")
  val AtAddinherit: Value = Value("")
  val AtDropinherit: Value = Value("")
  val AtAddof: Value = Value("")
  val AtDropof: Value = Value("")
  val AtReplicaidentity: Value = Value("")
  val AtEnablerowsecurity: Value = Value("")
  val AtDisablerowsecurity: Value = Value("")
  val AtForcerowsecurity: Value = Value("")
  val AtNoforcerowsecurity: Value = Value("")
  val AtGenericoptions: Value = Value("")
  val AtAttachpartition: Value = Value("")
  val AtDetachpartition: Value = Value("")
  val AtAddidentity: Value = Value("")
  val AtSetidentity: Value = Value("")
  val AtDropidentity: Value = Value("")
}
