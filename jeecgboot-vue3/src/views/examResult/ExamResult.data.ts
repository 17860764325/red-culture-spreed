import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
import { getWeekMonthQuarterYear } from '/@/utils';
//列表数据
export const columns: BasicColumn[] = [
   {
    title: '得分',
    align:"center",
    dataIndex: 'score'
   },
   {
    title: '课程编码',
    align:"center",
    dataIndex: 'objectCode'
   },
   {
    title: '创建人',
    align:"center",
    dataIndex: 'createBy'
   },
];
//查询数据
export const searchFormSchema: FormSchema[] = [
];
//表单数据
export const formSchema: FormSchema[] = [
  {
    label: '得分',
    field: 'score',
    component: 'InputNumber',
  },
  {
    label: '课程编码',
    field: 'objectCode',
    component: 'Input',
  },
  {
    label: '创建人',
    field: 'createBy',
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
  score: {title: '得分',order: 0,view: 'number', type: 'number',},
  objectCode: {title: '课程编码',order: 1,view: 'text', type: 'string',},
  createBy: {title: '创建人',order: 2,view: 'text', type: 'string',},
};

/**
* 流程表单调用这个方法获取formSchema
* @param param
*/
export function getBpmFormSchema(_formData): FormSchema[]{
  // 默认和原始表单保持一致 如果流程中配置了权限数据，这里需要单独处理formSchema
  return formSchema;
}