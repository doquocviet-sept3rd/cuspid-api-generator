export interface CuspidEntity {
  id: string;
  label?: string;
  icon?: string;
}

export const CUSPID_ENTITIES: CuspidEntity[] = [{
  id: 'Category'
}, {
  id: 'Field'
}, {
  id: 'GenerateType'
}, {
  id: 'Group'
}, {
  id: 'GroupMember'
}, {
  id: 'Option'
}, {
  id: 'Project'
}, {
  id: 'SQLType'
}, {
  id: 'Schema'
}, {
  id: 'Source'
}, {
  id: 'Table'
}, {
  id: 'TableRelation'
}, {
  id: 'User'
}];
