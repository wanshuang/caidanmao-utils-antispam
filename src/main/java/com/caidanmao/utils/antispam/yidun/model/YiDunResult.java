package com.caidanmao.utils.antispam.yidun.model;

import lombok.Data;
import lombok.ToString;


/**
 * @author ws
 * @date 2020/9/7
 */
@Data
@ToString
public class YiDunResult {

    /**
     * 本次请求数据标识，可以根据该标识查询数据最新结果
     */
    String taskId;

    /**
     * 图片检测状态码，定义为：0：检测成功，610：图片下载失败，620：图片格式错误，630：其它
     * 视频检测检测结果，0：成功，1：失败
     */
    Status status;

    /**
     * 建议动作，2：建议删除（不通过），1：建议审核（疑似），0：建议通过（通过）
     */
    Action action;


    /**
     * 审核模式，0：纯机审，1：机审+部分人审，2：机审+全量人审
     */
    Integer censorType;


    /**
     * json数组
     *
     * 文本策略
     * label	Number	分类信息，100：色情，200：广告，260：广告法，300：暴恐，400：违禁，500：涉政，600：谩骂，700：灌水，900：其他
     * subLabels	json数组	细分类信息，可能包含多个，可能为空
     * level	Number	分类级别，0：通过， 1：嫌疑，2：不通过
     * details	json对象	其他信息
     *
     * 图片策略
     * label	Number	分类信息，100：色情，110：性感低俗，200：广告，210：二维码，260：广告法，300：暴恐，400：违禁，500：涉政，900：其他
     * subLabels	json数组	细分类信息,可能包含多个，可能为空
     * level	json对象	分类级别，0：正常，1：不确定，2：确定
     * rate	Number	置信度分数，0-1之间取值，1为置信度最高，0为置信度最低。若level为正常，置信度越大，说明正常的可能性越高。若level为不确定或确定，置信度越大，说明垃圾的可能性越高
     *
     */
//    String labels;

    /**
     * 响应码
     */
    Integer code;

    /**
     * 相应信息
     */
    String msg;

    /**
     * 分类信息
     */
    Label label;


    public void setLabel(Integer labelCode) {
        this.label = Label.get(labelCode);
    }

    public enum Status {
        /**
         * 成功
         */
        success(0),
        /**
         * 失败
         */
        fail(1);

        private int code;

        Status(int code) {
            this.code = code;
        }
    }

    public enum Action {
        /**
         * 通过
         */
        pass(0),
        /**
         * 疑似
         */
        suspect(1),
        /**
         * 不通过
         */
        reject(2);

        private int code;

        Action(int code) {
            this.code = code;
        }
    }

    @ToString
    public enum Label {
        porn(100, "色情"),
        sex(110, "性感"),
        ad(200, "广告"),
        qr(210, "二维码"),
        fear(300, "暴恐"),
        prohibited(400, "违禁"),
        political(500, "涉政"),
        vulgar(600, "谩骂"),
        irrigation(700, "灌水"),
        other(900, "其他");

        private int code;
        private String content;

        Label(int code, String content) {
            this.code = code;
            this.content = content;
        }

        public static Label get(Integer code) {
            if (code == null) {
                return null;
            }
            for (Label label : values()) {
                if (label.code == code) {
                    return label;
                }
            }
            return null;
        }

        public int getCode() {
            return code;
        }

        public String getContent() {
            return content;
        }

    }
}
