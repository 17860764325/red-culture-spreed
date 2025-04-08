import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
import { getWeekMonthQuarterYear } from '/@/utils';
//列表数据
export const columns: BasicColumn[] = [
   {
    title: '章节名称',
    align:"center",
    dataIndex: 'chapterName'
   },
   {
    title: '章节编码',
    align:"center",
    dataIndex: 'chapterCode'
   },
   {
    title: '父章节id',
    align:"center",
    dataIndex: 'patientId'
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
    label: '章节名称',
    field: 'chapterName',
    component: 'Input',
  },
  {
    label: '章节编码',
    field: 'chapterCode',
    component: 'Input',
  },
  {
    label: '父章节id',
    field: 'patientId',
    component: 'Input',
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

//子表列表数据
export const objectDataColumns: BasicColumn[] = [
   {
    title: '章节编码',
    align:"center",
    dataIndex: 'chapterCode'
   },
   {
    title: '章节名称',
    align:"center",
    dataIndex: 'chapterName'
   },
   {
    title: '具体内容',
    align:"center",
    dataIndex: 'context',
   },
   {
    title: '视频内容',
    align:"center",
    dataIndex: 'vedio'
   },
];
//子表表单数据
export const objectDataFormSchema: FormSchema[] = [
  // TODO 子表隐藏字段，目前写死为ID
  {
    label: '',
    field: 'id',
    component: 'Input',
    show: false
  },
  {
    label: '章节编码',
    field: 'chapterCode',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入章节编码!'},
          ];
     },
  },
  {
    label: '章节名称',
    field: 'chapterName',
    component: 'Input',
  },
  {
    label: '具体内容',
    field: 'context',
    component: 'JEditor',
  },
  {
    label: '视频内容',
    field: 'vedio',
    component: 'JUpload',
  },
];

// 高级查询数据
export const superQuerySchema = {
  chapterName: {title: '章节名称',order: 0,view: 'text', type: 'string',},
  chapterCode: {title: '章节编码',order: 1,view: 'text', type: 'string',},
  patientId: {title: '父章节id',order: 2,view: 'text', type: 'string',},
};
