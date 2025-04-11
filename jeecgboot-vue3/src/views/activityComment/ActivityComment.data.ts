import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
import { getWeekMonthQuarterYear } from '/@/utils';
//列表数据
export const columns: BasicColumn[] = [
   {
    title: '头像链接',
    align:"center",
    dataIndex: 'avatar'
   },
   {
    title: '名称',
    align:"center",
    dataIndex: 'name'
   },
   {
    title: '被评论用户id',
    align:"center",
    dataIndex: 'replyToId'
   },
   {
    title: '评论用户id',
    align:"center",
    dataIndex: 'commentId'
   },
   {
    title: '顶部评论ID',
    align:"center",
    dataIndex: 'topToId'
   },
   {
    title: '回复内容',
    align:"center",
    dataIndex: 'content'
   },
   {
    title: '活动ID',
    align:"center",
    dataIndex: 'activityId'
   },
];
//查询数据
export const searchFormSchema: FormSchema[] = [
];
//表单数据
export const formSchema: FormSchema[] = [
  {
    label: '头像链接',
    field: 'avatar',
    component: 'Input',
  },
  {
    label: '名称',
    field: 'name',
    component: 'Input',
  },
  {
    label: '被评论用户id',
    field: 'replyToId',
    component: 'Input',
  },
  {
    label: '评论用户id',
    field: 'commentId',
    component: 'Input',
  },
  {
    label: '顶部评论ID',
    field: 'topToId',
    component: 'Input',
  },
  {
    label: '回复内容',
    field: 'content',
    component: 'InputTextArea',
  },
  {
    label: '活动ID',
    field: 'activityId',
    component: 'Input',
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
  avatar: {title: '头像链接',order: 0,view: 'text', type: 'string',},
  name: {title: '名称',order: 1,view: 'text', type: 'string',},
  replyToId: {title: '被评论用户id',order: 2,view: 'text', type: 'string',},
  commentId: {title: '评论用户id',order: 3,view: 'text', type: 'string',},
  topToId: {title: '顶部评论ID',order: 4,view: 'text', type: 'string',},
  content: {title: '回复内容',order: 5,view: 'textarea', type: 'string',},
  activityId: {title: '活动ID',order: 6,view: 'text', type: 'string',},
};

/**
* 流程表单调用这个方法获取formSchema
* @param param
*/
export function getBpmFormSchema(_formData): FormSchema[]{
  // 默认和原始表单保持一致 如果流程中配置了权限数据，这里需要单独处理formSchema
  return formSchema;
}
