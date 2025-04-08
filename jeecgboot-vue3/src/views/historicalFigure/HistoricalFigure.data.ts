import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
import { getWeekMonthQuarterYear } from '/@/utils';
//列表数据
export const columns: BasicColumn[] = [
   {
    title: '人物姓名',
    align:"center",
    dataIndex: 'name'
   },
   {
    title: '出生年份',
    align:"center",
    dataIndex: 'birthYear'
   },
   {
    title: '逝世年份',
    align:"center",
    dataIndex: 'deathYear'
   },
   {
    title: '主要事迹',
    align:"center",
    dataIndex: 'mainAchievement'
   },
   {
    title: '关联红色文化ID列表',
    align:"center",
    dataIndex: 'relatedCultureIds'
   },
];
//查询数据
export const searchFormSchema: FormSchema[] = [
];
//表单数据
export const formSchema: FormSchema[] = [
  {
    label: '人物姓名',
    field: 'name',
    component: 'Input',
  },
  {
    label: '出生年份',
    field: 'birthYear',
    component: 'Input',
  },
  {
    label: '逝世年份',
    field: 'deathYear',
    component: 'Input',
  },
  {
    label: '主要事迹',
    field: 'mainAchievement',
    component: 'InputTextArea',
  },
  {
    label: '关联红色文化ID列表',
    field: 'relatedCultureIds',
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
  name: {title: '人物姓名',order: 0,view: 'text', type: 'string',},
  birthYear: {title: '出生年份',order: 1,view: 'text', type: 'string',},
  deathYear: {title: '逝世年份',order: 2,view: 'text', type: 'string',},
  mainAchievement: {title: '主要事迹',order: 3,view: 'textarea', type: 'string',},
  relatedCultureIds: {title: '关联红色文化ID列表',order: 4,view: 'text', type: 'string',},
};

/**
* 流程表单调用这个方法获取formSchema
* @param param
*/
export function getBpmFormSchema(_formData): FormSchema[]{
  // 默认和原始表单保持一致 如果流程中配置了权限数据，这里需要单独处理formSchema
  return formSchema;
}