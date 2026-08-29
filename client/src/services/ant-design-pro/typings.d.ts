// @ts-ignore
/* eslint-disable */

declare namespace API {
  type CurrentUser = {
    id?: string;
    username?: string;
    account?: string;
    avatar?: string;
    email?: string;
    phone?: string;
    address?: string;
    gender?: number;
  };

  // 返回用户信息
  type UserInfoVO = {
    username?: string;
    account: string;
    avatar?: string;
    gender?: string;
    phone?: string;
    email?: string;
  }

  type LoginResult = {
    token?: string;
    userInfo?: UserInfoVO;
    status?: string;
    type?: string;
    currentAuthority?: string;
  };

  type RegisterResult = string

  type PageParams = {
    current?: number;
    pageSize?: number;
  };

  type RuleListItem = {
    key?: number;
    disabled?: boolean;
    href?: string;
    avatar?: string;
    name?: string;
    owner?: string;
    desc?: string;
    callNo?: number;
    status?: number;
    updatedAt?: string;
    createdAt?: string;
    progress?: number;
  };

  type RuleList = {
    data?: RuleListItem[];
    /** 列表的内容总数 */
    total?: number;
    success?: boolean;
  };

  type FakeCaptcha = {
    code?: number;
    status?: string;
  };

  type LoginParams = {
    account?: string;
    password?: string;
    autoLogin?: boolean;
    type?: string;
  };

  type RegisterParams = {
    account?: string;
    password?: string;
    confirmPassword?: boolean;
    type?: string;
  };

  type ErrorResponse = {
    /** 业务约定的错误码 */
    errorCode: string;
    /** 业务上的错误信息 */
    errorMessage?: string;
    /** 业务上的请求是否成功 */
    success?: boolean;
  };

  type NoticeIconList = {
    data?: NoticeIconItem[];
    /** 列表的内容总数 */
    total?: number;
    success?: boolean;
  };

  type NoticeIconItemType = 'notification' | 'message' | 'event';

  type NoticeIconItem = {
    id?: string;
    extra?: string;
    key?: string;
    read?: boolean;
    avatar?: string;
    title?: string;
    status?: string;
    datetime?: string;
    description?: string;
    type?: NoticeIconItemType;
  };
}
