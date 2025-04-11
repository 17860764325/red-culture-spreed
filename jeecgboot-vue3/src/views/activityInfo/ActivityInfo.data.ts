import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
import { getWeekMonthQuarterYear } from '/@/utils';
//列表数据
export const columns: BasicColumn[] = [
   {
    title: '活动名称',
    align:"center",
    dataIndex: 'activityName'
   },
   {
    title: '开始时间',
    align:"center",
    dataIndex: 'startTime'
   },
   {
    title: '结束时间',
    align:"center",
    dataIndex: 'endTime'
   },
   {
    title: '活动地点',
    align:"center",
    dataIndex: 'address'
   },
   {
    title: '人数限制',
    align:"center",
    dataIndex: 'maxPeople'
   },
   {
    title: '已报名人数',
    align:"center",
    dataIndex: 'signupCount'
   },
   {
    title: '封面图',
    align:"center",
    dataIndex: 'coverImage'
   },
];
//查询数据
export const searchFormSchema: FormSchema[] = [
];
//表单数据
export const formSchema: FormSchema[] = [
  {
    label: '活动名称',
    field: 'activityName',
    component: 'Input',
  },
  {
    label: '开始时间',
    field: 'startTime',
    component: 'DatePicker',
    componentProps: {
       showTime: true,
       valueFormat: 'YYYY-MM-DD HH:mm:ss'
     },
  },
  {
    label: '结束时间',
    field: 'endTime',
    component: 'DatePicker',
    componentProps: {
       showTime: true,
       valueFormat: 'YYYY-MM-DD HH:mm:ss'
     },
  },
  {
    label: '活动地点',
    field: 'address',
    component: 'Input',
  },
  {
    label: '人数限制',
    field: 'maxPeople',
    component: 'InputNumber',
  },
  {
    label: '已报名人数',
    field: 'signupCount',
    component: 'InputNumber',
  },
  {
    label: '封面图',
    field: 'coverImage',
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
  activityName: {title: '活动名称',order: 0,view: 'text', type: 'string',},
  startTime: {title: '开始时间',order: 1,view: 'datetime', type: 'string',},
  endTime: {title: '结束时间',order: 2,view: 'datetime', type: 'string',},
  address: {title: '活动地点',order: 3,view: 'text', type: 'string',},
  maxPeople: {title: '人数限制',order: 4,view: 'number', type: 'number',},
  signupCount: {title: '已报名人数',order: 5,view: 'number', type: 'number',},
  coverImage: {title: '封面图',order: 6,view: 'text', type: 'string',},
};

/**
* 流程表单调用这个方法获取formSchema
* @param param
*/
export function getBpmFormSchema(_formData): FormSchema[]{
  // 默认和原始表单保持一致 如果流程中配置了权限数据，这里需要单独处理formSchema
  return formSchema;
}
