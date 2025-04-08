import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
import { getWeekMonthQuarterYear } from '/@/utils';
//列表数据
export const columns: BasicColumn[] = [
   {
    title: '问题标题',
    align:"center",
    dataIndex: 'question'
   },
   {
    title: '选项A',
    align:"center",
    dataIndex: 'optionA'
   },
   {
    title: '选项B',
    align:"center",
    dataIndex: 'optionB'
   },
   {
    title: '选项C',
    align:"center",
    dataIndex: 'optionC'
   },
   {
    title: '选项D',
    align:"center",
    dataIndex: 'optionD'
   },
   {
    title: '答案(数字，从a-d依次为1-4)',
    align:"center",
    dataIndex: 'answer'
   },
   {
    title: '课程编码',
    align:"center",
    dataIndex: 'objectCode'
   },
];
//查询数据
export const searchFormSchema: FormSchema[] = [
];
//表单数据
export const formSchema: FormSchema[] = [
  {
    label: '问题标题',
    field: 'question',
    component: 'Input',
  },
  {
    label: '选项A',
    field: 'optionA',
    component: 'Input',
  },
  {
    label: '选项B',
    field: 'optionB',
    component: 'Input',
  },
  {
    label: '选项C',
    field: 'optionC',
    component: 'Input',
  },
  {
    label: '选项D',
    field: 'optionD',
    component: 'Input',
  },
  {
    label: '答案(数字，从a-d依次为1-4)',
    field: 'answer',
    component: 'InputNumber',
  },
  {
    label: '课程编码',
    field: 'objectCode',
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
  question: {title: '问题标题',order: 0,view: 'text', type: 'string',},
  optionA: {title: '选项A',order: 1,view: 'text', type: 'string',},
  optionB: {title: '选项B',order: 2,view: 'text', type: 'string',},
  optionC: {title: '选项C',order: 3,view: 'text', type: 'string',},
  optionD: {title: '选项D',order: 4,view: 'text', type: 'string',},
  answer: {title: '答案(数字，从a-d依次为1-4)',order: 5,view: 'number', type: 'number',},
  objectCode: {title: '课程编码',order: 6,view: 'text', type: 'string',},
};

/**
* 流程表单调用这个方法获取formSchema
* @param param
*/
export function getBpmFormSchema(_formData): FormSchema[]{
  // 默认和原始表单保持一致 如果流程中配置了权限数据，这里需要单独处理formSchema
  return formSchema;
}