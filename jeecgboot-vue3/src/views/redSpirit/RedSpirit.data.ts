import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import {rules} from '/@/utils/helper/validator';
import {render} from '/@/utils/common/renderUtils';
import {getWeekMonthQuarterYear} from '/@/utils';
//列表数据
export const columns: BasicColumn[] = [
  {
    title: '文章类型',
    align: "center",
    dataIndex: 'pageType',
    customRender: ({value}) => {
      return render.renderDict(value, 'pageTypeDict');
    }
  },
  {
    title: '精神名称',
    align: "center",
    dataIndex: 'spiritName'
  },
  {
    title: '概述1',
    align: "center",
    dataIndex: 'overviewOne'
  },
  {
    title: '概述二',
    align: "center",
    dataIndex: 'overviewTwo'
  },
  {
    title: '概述三',
    align: "center",
    dataIndex: 'overviewThree'
  },
  {
    title: '概述四',
    align: "center",
    dataIndex: 'overviewFour'
  },
  {
    title: '文章编码',
    align: "center",
    dataIndex: 'pageNumber'
  },
];
//查询数据
export const searchFormSchema: FormSchema[] = [];

//表单数据
export const formSchema: FormSchema[] = [
  {
    label: '文章类型',
    field: 'pageType',
    component: 'JDictSelectTag',
    componentProps:{
      dictCode:"pageTypeDict"
    },
  },
  {
    label: '精神名称',
    field: 'spiritName',
    component: 'Input',
    dynamicRules: ({model, schema}) => {
      return [
        {required: true, message: '请输入精神名称!'},
      ];
    },
  },
  {
    label: '概述1',
    field: 'overviewOne',
    component: 'Input',
  },
  {
    label: '概述二',
    field: 'overviewTwo',
    component: 'Input',
  },
  {
    label: '概述三',
    field: 'overviewThree',
    component: 'Input',
  },
  {
    label: '概述四',
    field: 'overviewFour',
    component: 'Input',
  },
  {
    label: '文章编码',
    field: 'pageNumber',
    component: 'Input',
    dynamicRules: ({model, schema}) => {
      return [
        {required: true, message: '请输入文章编码!'},
      ];
    },
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
export const pageInfoColumns: BasicColumn[] = [
  {
    title: '文章编码',
    align: "center",
    dataIndex: 'pageNumber'
  },
  {
    title: '文章标题',
    align: "center",
    dataIndex: 'pageTitle'
  },
  {
    title: '文章小标题',
    align: "center",
    dataIndex: 'pageLittleTitle'
  },
  {
    title: '文章作者以及发布时间',
    align: "center",
    dataIndex: 'pageDetaWriter'
  },
  {
    title: '文章正文，段落通过 | 进行分割',
    align: "center",
    dataIndex: 'pageContext'
  },
];
//子表表单数据
export const pageInfoFormSchema: FormSchema[] = [
  // TODO 子表隐藏字段，目前写死为ID
  {
    label: '',
    field: 'id',
    component: 'Input',
    show: false
  },
  {
    label: '文章编码',
    field: 'pageNumber',
    component: 'Input',
    show: false
  },
  {
    label: '文章标题',
    field: 'pageTitle',
    component: 'Input',
  },
  {
    label: '文章小标题',
    field: 'pageLittleTitle',
    component: 'Input',
  },
  {
    label: '文章作者以及发布时间',
    field: 'pageDetaWriter',
    component: 'Input',
  },
  {
    label: '文章正文，段落通过 | 进行分割',
    field: 'pageContext',
    component: 'InputTextArea',
  },
];

// 高级查询数据
export const superQuerySchema = {
  spiritName: {title: '精神名称', order: 0, view: 'text', type: 'string',},
  overviewOne: {title: '概述1', order: 1, view: 'text', type: 'string',},
  overviewTwo: {title: '概述二', order: 2, view: 'text', type: 'string',},
  overviewThree: {title: '概述三', order: 3, view: 'text', type: 'string',},
  overviewFour: {title: '概述四', order: 4, view: 'text', type: 'string',},
  pageNumber: {title: '文章编码', order: 5, view: 'text', type: 'string',},
  pageType: {title: '文章类型', order: 6, view: 'JDictSelectTag', type: 'string',},
};
