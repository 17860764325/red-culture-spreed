import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
import { getWeekMonthQuarterYear } from '/@/utils';
//列表数据
export const columns: BasicColumn[] = [
   {
    title: '表名',
    align:"center",
    dataIndex: 'tableName'
   },
   {
    title: '数据ID',
    align:"center",
    dataIndex: 'tableDataId'
   },
   {
    title: '来源用户id',
    align:"center",
    dataIndex: 'fromUserId'
   },
   {
    title: '发送给用户id(允许为空)',
    align:"center",
    dataIndex: 'toUserId'
   },
   {
    title: '评论id(允许为空，不为空时，则为回复)',
    align:"center",
    dataIndex: 'commentId'
   },
   {
    title: '回复内容',
    align:"center",
    dataIndex: 'commentContent'
   },
];
//查询数据
export const searchFormSchema: FormSchema[] = [
];
//表单数据
export const formSchema: FormSchema[] = [
  {
    label: '表名',
    field: 'tableName',
    component: 'Input',
  },
  {
    label: '数据ID',
    field: 'tableDataId',
    component: 'Input',
  },
  {
    label: '来源用户id',
    field: 'fromUserId',
    component: 'Input',
  },
  {
    label: '发送给用户id(允许为空)',
    field: 'toUserId',
    component: 'Input',
  },
  {
    label: '评论id(允许为空，不为空时，则为回复)',
    field: 'commentId',
    component: 'Input',
  },
  {
    label: '回复内容',
    field: 'commentContent',
    component: 'InputTextArea',
  },
	// TODO 主键隐藏字段，目前写死为ID
	{
	  label: '',
	  field: 'id',
	  component: 'Input',
	  show: false
	},
];

// 高级查询数据
export const superQuerySchema = {
  tableName: {title: '表名',order: 0,view: 'text', type: 'string',},
  tableDataId: {title: '数据ID',order: 1,view: 'text', type: 'string',},
  fromUserId: {title: '来源用户id',order: 2,view: 'text', type: 'string',},
  toUserId: {title: '发送给用户id(允许为空)',order: 3,view: 'text', type: 'string',},
  commentId: {title: '评论id(允许为空，不为空时，则为回复)',order: 4,view: 'text', type: 'string',},
  commentContent: {title: '回复内容',order: 5,view: 'textarea', type: 'string',},
};

/**
* 流程表单调用这个方法获取formSchema
* @param param
*/
export function getBpmFormSchema(_formData): FormSchema[]{
  // 默认和原始表单保持一致 如果流程中配置了权限数据，这里需要单独处理formSchema
  return formSchema;
}