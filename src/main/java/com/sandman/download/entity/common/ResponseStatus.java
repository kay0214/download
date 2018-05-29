package com.sandman.download.entity.common;

/**
 * Created by sunpeikai on 2018/5/29.
 */
public enum ResponseStatus {
    /*
        200，操作成功
        401，上传文件为空
        402，上传远程服务器失败
        403, 下载积分不足
        404, 下载出错
        405, 无权修改
        406, 无权删除
        407, 删除失败
        408, 资源不存在
        409, 用户名已存在
        410, 请勿重复上传
        411, 登录密码错误
        412, 单个文件最大220MB
        413, 请先填写联系方式
        414, 邮件验证码发送失败
        415, 创建用户你带什么ID啊
        416, 请先填写验证码
        417, 验证码不正确
        418, 未发送验证码或验证码已过期
        419, 用户未登录
        420, 资源积分必填
        421, 资源描述必须10个字符以上
        422, 无数据
        423, 用户不存在
        424, 账户已被锁定,如需解锁请联系管理员
        425, 请先填写联系方式 --校验联系方式是否被占用
        426, 联系方式已经被绑定 --校验联系方式是否被占用
    * */
    SUCCESS(200, "请求成功"),
    UPLOAD_FILE_EMPTY(401, "上传文件为空"),
    UPLOAD_SERVER_FAILED(402, "上传远程服务器失败"),
    DOWNLOAD_GOLD_NOT_ENOUGH(403,"下载积分不足"),
    DOWNLOAD_FAILED(404,"下载出错"),
    NOT_HAVE_PERMISSION_TO_UPDATE(405,"无权修改"),
    NOT_HAVE_PERMISSION_TO_DELETE(406,"无权删除"),
    DELETE_FAILED(407,"删除失败"),
    RESOURCE_IS_NOT_EXIST(408,"资源不存在"),
    USERNAME_IS_EXIST(409,"用户名已存在"),
    DO_NOT_REPEAT_UPLOAD(410,"请勿重复上传"),
    PASSWORD_MISTAKE(411,"登录密码错误"),
    ONE_FILE_MAX_SIZE_IS_220MB(412,"单个文件最大220MB"),
    CONTACT_IS_EMPTY(413,"请先填写联系方式"),
    EMAIL_VERIFYCODE_SEND_FAILED(414,"邮件验证码发送失败"),
    CREATE_USER_MUST_WITHOUT_ID(415,"创建用户你带什么ID啊"),
    VERIFYCODE_IS_EMPTY(416,"请先填写验证码"),
    VERIFYCODE_MISTAKE(417,"验证码不正确"),
    NOT_SEND_VERIFYCODE_OR_VERIFYCODE_OVERDUE(418,"未发送验证码或验证码已过期"),
    USER_NOT_LOGIN(419,"用户未登录"),
    RESOURCE_GOLD_IS_EMPTY(420,"资源积分必填"),
    RESOURCE_DESC_MIN_SIZE_IS_10CHAR(421,"资源描述必须10个字符以上"),
    HAVE_NO_DATA(422,"无数据"),
    ACCOUNT_IS_NOT_EXIST(423,"用户不存在"),
    ACCOUNT_IS_LOCKED(424,"账户已被锁定,如需解锁请联系管理员"),
    CONTACT_IS_EMPTY_USED_BY_VERIFY_CONTACT(425,"请先填写联系方式"),
    CONTACT_IS_BOUND(426,"联系方式已经被绑定");
    private final Integer status;
    private final String reason;

    ResponseStatus(Integer status, String reason) {
        this.status = status;
        this.reason = reason;

    }

    public Integer getStatus() {
        return status;
    }

    public String getReason() {
        return reason;
    }
}
