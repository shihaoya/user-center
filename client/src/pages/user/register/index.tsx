import {LockOutlined, UserOutlined,} from '@ant-design/icons';
import {LoginForm, ProFormText,} from '@ant-design/pro-components';
import {Helmet, history, useModel} from '@umijs/max';

import {Alert, App, Button, Tabs} from 'antd';
import {createStyles} from 'antd-style';
import React, {useState} from 'react';
import {Footer} from '@/components';
import {register} from '@/services/ant-design-pro/api';
import Settings from '../../../../config/defaultSettings';
import {SYSTEM_LOGO} from "@/constants";

/**
 * Validate redirect URL to prevent open redirect attacks.
 * Only allow same-origin relative paths starting with '/'.
 */
const getSafeRedirectUrl = (redirect: string | null): string => {
  if (!redirect?.startsWith('/')) return '/';

  if (redirect.startsWith('//')) return '/';

  try {
    const parsed = new URL(redirect, window.location.origin);
    if (parsed.origin !== window.location.origin) return '/';
    return `${parsed.pathname}${parsed.search}${parsed.hash}`;
  } catch {
    return '/';
  }
};

const useStyles = createStyles(({ token }) => {
  return {
    action: {
      marginLeft: '8px',
      color: 'rgba(0, 0, 0, 0.2)',
      fontSize: '24px',
      verticalAlign: 'middle',
      cursor: 'pointer',
      transition: 'color 0.3s',
      '&:hover': {
        color: token.colorPrimaryActive,
      },
    },
    lang: {
      width: 42,
      height: 42,
      lineHeight: '42px',
      position: 'fixed',
      right: 16,
      borderRadius: token.borderRadius,
      ':hover': {
        backgroundColor: token.colorBgTextHover,
      },
    },
    container: {
      display: 'flex',
      flexDirection: 'column',
      height: '100vh',
      overflow: 'auto',
      backgroundImage:
        "url('https://mdn.alipayobjects.com/yuyan_qk0oxh/afts/img/V-_oS6r-i7wAAAAAAAAAAAAAFl94AQBr')",
      backgroundSize: '100% 100%',
    },
  };
});

const LoginMessage: React.FC<{
  content: string;
}> = ({ content }) => {
  return (
    <Alert
      style={{
        marginBottom: 24,
      }}
      title={content}
      type="error"
      showIcon
    />
  );
};

const Register: React.FC = () => {
  const [userLoginState, setUserLoginState] = useState<API.LoginResult>({});
  const [type, setType] = useState<string>('account');
  const { initialState, setInitialState } = useModel('@@initialState');
  const { styles } = useStyles();
  const { message } = App.useApp();

  const handleSubmit = async (values: API.RegisterParams) => {
    try {
      // 注册
      const res = await register({ ...values, type });
      if (res) {
        message.success('注册成功！');
        toLogin()
        return;
      }
    } catch {
      const defaultLoginFailureMessage = '登录失败，请重试！';
      message.error(defaultLoginFailureMessage);
    }
  };
  function toLogin () {
    history.push(`/user/login`);
  }
  return (
    <div className={styles.container}>
      <Helmet>
        <title>
          注册
          {Settings.title && ` - ${Settings.title}`}
        </title>
      </Helmet>
      <div
        style={{
          flex: '1',
          padding: '32px 0',
        }}
      >
        <LoginForm
          submitter={{
            searchConfig: {
              submitText: '注册'
            }
          }}
          contentStyle={{
            minWidth: 280,
            maxWidth: '75vw',
          }}
          logo={<img alt="logo" src={SYSTEM_LOGO} />}
          title="用户中心"
          subTitle={'用于学习的用户中心'}
          onFinish={async (values) => {
            await handleSubmit(values as API.LoginParams);
          }}
        >
          <Tabs
            activeKey={type}
            onChange={setType}
            centered
            items={[
              {
                key: 'account',
                label: '账户密码注册',
              },
            ]}
          />

          {type === 'account' && (
            <>
              <ProFormText
                name="account"
                fieldProps={{
                  size: 'large',
                  prefix: <UserOutlined />,
                }}
                placeholder="请输入用户名"
                rules={[
                  {
                    required: true,
                    message: '用户名是必填项！',
                  }, {
                    min: 4,
                    max: 16,
                    message: '用户名在4~16位之间！'
                  }
                ]}
              />
              <ProFormText
                name="planetCode"
                fieldProps={{
                  size: 'large',
                  prefix: <UserOutlined />,
                }}
                placeholder="请输入星球编号"
                rules={[
                  {
                    required: true,
                    message: '星球编号是必填项！',
                  }
                ]}
              />
              <ProFormText.Password
                name="password"
                fieldProps={{
                  size: 'large',
                  prefix: <LockOutlined />,
                }}
                placeholder="请输入密码"
                rules={[
                  {
                    required: true,
                    message: '密码是必填项！',
                  }, {
                    min: 8,
                    max: 16,
                    message: '密码在8~16位之间！'
                  }
                ]}
              />
              <ProFormText.Password
                name="confirmPassword"
                fieldProps={{
                  size: 'large',
                  prefix: <LockOutlined />,
                }}
                placeholder="请输入确认密码"
                rules={[
                  {
                    required: true,
                    message: '确认密码是必填项！',
                  }, {
                    min: 8,
                    max: 16,
                    message: '确认密码在8~16位之间！'
                  }, ({ getFieldValue }) => ({
                    validator: (_, value) => {
                      if (!value || getFieldValue('password') === value) {
                        return Promise.resolve();
                      }
                      return Promise.reject(new Error('两次输入密码不一致！'));
                    }
                  })
                ]}
              />
            </>
          )}
          <div
            style={{
              marginBottom: 24,
            }}
          >
            <Button
              type="link"
              onClick={toLogin}
              style={{
                float: 'right',
                padding: 0,
              }}
            >
              已有账号 ?
            </Button>
          </div>
        </LoginForm>
      </div>
      <Footer />
    </div>
  );
};

export default Register;
