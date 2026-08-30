import type { RequestOptions } from '@@/plugin-request/request';
import type { RequestConfig } from '@umijs/max';

import { message, notification } from 'antd';

// 错误处理方案： 错误类型
enum ErrorShowType {
  SILENT = 0,
  WARN_MESSAGE = 1,
  ERROR_MESSAGE = 2,
  NOTIFICATION = 3,
  REDIRECT = 9,
}
// 与后端约定的响应数据格式
interface ResponseStructure {
  success: boolean;
  data: unknown;
  errorCode?: number;
  errorMessage?: string;
  showType?: ErrorShowType;
}

/** 后端统一响应格式：com.hao.usercenter.common.BaseResponse */
interface BaseResponse {
  code: number;
  msg: string;
  desc: string;
  data: any;
}

/** 业务成功码，与后端 ErrorCode.SUCCESS 保持一致 */
const SUCCESS_CODE = 200;

/**
 * @name 错误处理
 * pro 自带的错误处理， 可以在这里做自己的改动
 * @doc https://umijs.org/docs/max/request#配置
 */
export const errorConfig: RequestConfig = {

  // 请求拦截器
  requestInterceptors: [
    (config: RequestOptions) => {
      // 拦截请求配置，进行个性化处理。
      // 示例：为请求附加 token（按需启用）
      // const token = localStorage.getItem('token');
      // if (token) {
      //   config.headers = { ...config.headers, Authorization: `Bearer ${token}` };
      // }
      return config;
    },
  ],

  // 响应拦截器
  responseInterceptors: [
    (response) => {
      // 后端返回的是 { code, msg, desc, data }
      const res = response.data as unknown as BaseResponse;

      if (res?.code !== SUCCESS_CODE) {
        message.error(res?.msg ?? '响应错误')
      }

      // umi封装的很垃圾，编译源码里自己的拦截器在最下面，没办法正常解包，只能覆盖一层了，最后会返回response.data
      response.data = res.data
      return response;
    }
  ],
};
