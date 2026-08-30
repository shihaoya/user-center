import type {ActionType, ProColumns} from '@ant-design/pro-components';
import {ProTable, TableDropdown} from '@ant-design/pro-components';
import {message} from 'antd';
import {useRef} from 'react';
import {users} from "@/services/ant-design-pro/api";

const columns: ProColumns<API.CurrentUser>[] = [
  {
    dataIndex: 'index',
    valueType: 'indexBorder',
    width: 48,
  },
  {
    title: '头像',
    dataIndex: 'avatar',
    valueType: 'avatar',
    search: false,
  },
  {
    title: '账号',
    dataIndex: 'account',
    copyable: true,
    ellipsis: true,
  },
  {
    title: '星球编号',
    dataIndex: 'planetCode',
    copyable: true,
    ellipsis: true,
  },
  {
    title: '昵称',
    dataIndex: 'username',
    ellipsis: true,
  },
  {
    title: '邮箱',
    dataIndex: 'email',
    ellipsis: true,
  },
  {
    title: '手机号',
    dataIndex: 'phone',
    ellipsis: true,
  },
  {
    disable: true,
    title: '性别',
    dataIndex: 'gender',
    valueType: 'select',
    valueEnum: {
      0: {
        text: '男',
        status: 'Success',
      },
      1: {
        text: '女',
        status: 'Error',
      },
    },
  },
  {
    disable: true,
    title: '角色',
    dataIndex: 'role',
    valueType: 'select',
    valueEnum: {
      1: {
        text: '管理员',
      },
      2: {
        text: '普通用户',
      },
    },
  },
  {
    title: '创建时间',
    key: 'showTime',
    dataIndex: 'createTime',
    valueType: 'date',
    sorter: true,
    search: false,
  },
  {
    title: '创建时间',
    dataIndex: 'createTime',
    valueType: 'dateRange',
    hideInTable: true,
    search: {
      transform: (value) => {
        return {
          startTime: value[0],
          endTime: value[1],
        };
      },
    },
  },
  {
    title: '更新时间',
    key: 'showTime',
    dataIndex: 'updateTime',
    valueType: 'date',
    sorter: true,
    search: false,
  },
  {
    title: '更新时间',
    dataIndex: 'updateTime',
    valueType: 'dateRange',
    hideInTable: true,
    search: {
      transform: (value) => {
        return {
          startTime: value[0],
          endTime: value[1],
        };
      },
    },
  },
  {
    title: '操作',
    valueType: 'option',
    key: 'option',
    render: (text, record, _, action) => [
      <a
        key="editable"
        onClick={() => {
          action?.startEditable?.(record.id);
        }}
      >
        编辑
      </a>,
      <a href={record.url} target="_blank" rel="noopener noreferrer" key="view">
        查看
      </a>,
      <TableDropdown
        key="actionGroup"
        onSelect={() => action?.reload()}
        menus={[
          { key: 'copy', name: '复制' },
          { key: 'delete', name: '删除' },
        ]}
      />,
    ],
  },
];

const Demo = () => {
  const actionRef = useRef<ActionType>();
  return (
    <ProTable<API.CurrentUser>
      columns={columns}
      actionRef={actionRef}
      cardBordered
      request={async (params) => {
        return users(params).then((res) => {
          return { data:res };
        })
      }}
      editable={{
        type: 'multiple',
      }}
      columnsState={{
        persistenceKey: 'pro-table-singe-demos',
        persistenceType: 'localStorage',
        defaultValue: {
          option: { fixed: 'right', disable: true },
        },
        onChange: () => {
          message.info('列设置已更新');
        },
      }}
      rowKey="id"
      search={{
        labelWidth: 'auto',
      }}
      options={{
        setting: {
          listsHeight: 400,
        },
      }}
      form={{
        syncToUrl: (values, type) => {
          if (type === 'get') {
            return {
              ...values,
              created_at: [values.startTime, values.endTime],
            };
          }
          return values;
        },
      }}
      pagination={{
        pageSize: 5,
        onChange: (page, pageSize) => {
          message.info(`第 ${page} 页${pageSize ? `，每页 ${pageSize} 条` : ''}`);
        },
      }}
      dateFormatter="string"
      headerTitle="Issue 管理"
    />
  );
};

export default () => (
  <div style={{ padding: 24 }}>
    <Demo />
  </div>
);
