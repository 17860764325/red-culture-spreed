import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
import { getWeekMonthQuarterYear } from '/@/utils';
//列表数据
export const columns: BasicColumn[] = [
   {
    title: '用户账号',
    align:"center",
    dataIndex: 'userAccount'
   },
   {
    title: '用户名称',
    align:"center",
    dataIndex: 'userName'
   },
   {
    title: '学习次数',
    align:"center",
    dataIndex: 'studyTimes'
   },
   {
    title: '学习时长',
    align:"center",
    dataIndex: 'studyTime'
   },
   {
    title: '发帖次数',
    align:"center",
    dataIndex: 'sendMessages'
   },
];
//查询数据
export const searchFormSchema: FormSchema[] = [
];
//表单数据
export const formSchema: FormSchema[] = [
  {
    label: '用户账号',
    field: 'userAccount',
    component: 'Input',
  },
  {
    label: '用户名称',
    field: 'userName',
    component: 'Input',
  },
  {
    label: '学习次数',
    field: 'studyTimes',
    component: 'Input',
  },
  {
    label: '学习时长',
    field: 'studyTime',
    component: 'Input',
  },
  {
    label: '发帖次数',
    field: 'sendMessages',
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
  userAccount: {title: '用户账号',order: 0,view: 'text', type: 'string',},
  userName: {title: '用户名称',order: 1,view: 'text', type: 'string',},
  studyTimes: {title: '学习次数',order: 2,view: 'text', type: 'string',},
  studyTime: {title: '学习时长',order: 3,view: 'text', type: 'string',},
  sendMessages: {title: '发帖次数',order: 4,view: 'text', type: 'string',},
};

/**
* 流程表单调用这个方法获取formSchema
* @param param
*/
export function getBpmFormSchema(_formData): FormSchema[]{
  // 默认和原始表单保持一致 如果流程中配置了权限数据，这里需要单独处理formSchema
  return formSchema;
}