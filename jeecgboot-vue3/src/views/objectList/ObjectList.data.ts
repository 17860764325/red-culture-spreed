import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
import { getWeekMonthQuarterYear } from '/@/utils';
//列表数据
export const columns: BasicColumn[] = [
   {
    title: '课程编码',
    align:"center",
    dataIndex: 'objectCode'
   },
   {
    title: '课程名称',
    align:"center",
    dataIndex: 'objectName'
   },
   {
    title: '课程概述',
    align:"center",
    dataIndex: 'objectRemark'
   },
   {
    title: '课程教师',
    align:"center",
    dataIndex: 'objectTeacher'
   },
   {
    title: '课程图片',
    align:"center",
    dataIndex: 'objectImg'
   },
];
//查询数据
export const searchFormSchema: FormSchema[] = [
];
//表单数据
export const formSchema: FormSchema[] = [
  {
    label: '课程编码',
    field: 'objectCode',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入课程编码!'},
          ];
     },
  },
  {
    label: '课程名称',
    field: 'objectName',
    component: 'Input',
  },
  {
    label: '课程概述',
    field: 'objectRemark',
    component: 'Input',
  },
  {
    label: '课程教师',
    field: 'objectTeacher',
    component: 'Input',
  },
  {
    label: '课程图片',
    field: 'objectImg',
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
  objectCode: {title: '课程编码',order: 0,view: 'text', type: 'string',},
  objectName: {title: '课程名称',order: 1,view: 'text', type: 'string',},
  objectRemark: {title: '课程概述',order: 2,view: 'text', type: 'string',},
  objectTeacher: {title: '课程教师',order: 3,view: 'text', type: 'string',},
  objectImg: {title: '课程图片',order: 4,view: 'text', type: 'string',},
};

/**
* 流程表单调用这个方法获取formSchema
* @param param
*/
export function getBpmFormSchema(_formData): FormSchema[]{
  // 默认和原始表单保持一致 如果流程中配置了权限数据，这里需要单独处理formSchema
  return formSchema;
}