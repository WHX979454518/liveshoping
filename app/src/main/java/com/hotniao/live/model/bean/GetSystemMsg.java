package com.hotniao.live.model.bean;

import java.util.List;

/**
 * @创建者 阳石柏
 * @创建时间 2016/10/28 15:20
 * @描述 ${获取系统消息返回data}
 */
public class GetSystemMsg {


    /**
     * system_dialog : [{"dialog_id":"122","msg":"{\"type\":\"general\",\"data\":{\"content\":\"\\u6d4b\\u8bd5\"}}","time":"1516697950"},{"dialog_id":"113","msg":"{\"type\":\"general\",\"data\":{\"content\":\"恭喜升级至1级\",\"url\":\"\"}}","time":"1516695901"},{"dialog_id":"97","msg":"{\"type\":\"general\",\"data\":{\"content\":\"1221212\"}}","time":"1516685966"},{"dialog_id":"96","msg":"{\"type\":\"general\",\"data\":{\"content\":\"asdasd\"}}","time":"1516685898"},{"dialog_id":"95","msg":"{\"type\":\"general\",\"data\":{\"content\":\"asdasd\"}}","time":"1516685696"},{"dialog_id":"94","msg":"{\"type\":\"general\",\"data\":{\"content\":\"asdasd\"}}","time":"1516685688"},{"dialog_id":"93","msg":"{\"type\":\"general\",\"data\":{\"content\":\"asdasd\"}}","time":"1516685592"},{"dialog_id":"92","msg":"{\"type\":\"general\",\"data\":{\"content\":\"asdasd\"}}","time":"1516685579"},{"dialog_id":"91","msg":"{\"type\":\"general\",\"data\":{\"content\":\"asdasd\"}}","time":"1516685530"},{"dialog_id":"90","msg":"{\"type\":\"general\",\"data\":{\"content\":\"asdasd\"}}","time":"1516685518"},{"dialog_id":"87","msg":"{\"type\":\"general\",\"data\":{\"content\":\"\\u6d4b\\u8bd5\\u8f6e\\u64ad\\u56fe\\u5185\\u94fe\\u7684\"}}","time":"1516679790"},{"dialog_id":"86","msg":"{\"type\":\"general\",\"data\":{\"content\":\"\\u6d4b\\u8bd5\\u8f6e\\u64ad\\u56fe\\u5185\\u94fe\\u7684\"}}","time":"1516679784"},{"dialog_id":"84","msg":"{\"type\":\"general\",\"data\":{\"content\":\"\\u6d4b\\u8bd5\\u8f6e\\u64ad\\u56fe\\u5185\\u94fe\\u7684\"}}","time":"1516679768"},{"dialog_id":"47","msg":"{\"type\":\"follow\",\"data\":{\"user_id\":\"10002\",\"content\":\"一片冷清关注了你\"}}","time":"1516348115"},{"dialog_id":"41","msg":"{\"type\":\"follow\",\"data\":{\"user_id\":\"10003\",\"content\":\"阳光关注了你\"}}","time":"1516327774"},{"dialog_id":"6","msg":"{\"type\":\"certification\",\"data\":{\"content\":\"主播认证已经通过了，快去直播吧~\",\"status\":\"Y\"}}","time":"1515998808"}]
     */

    private List<SystemDialogEntity> system_dialog;

    public void setSystem_dialog(List<SystemDialogEntity> system_dialog) {
        this.system_dialog = system_dialog;
    }

    public List<SystemDialogEntity> getSystem_dialog() {
        return system_dialog;
    }

    public static class SystemDialogEntity {
        /**
         * dialog_id : 122
         * msg : {"type":"general","data":{"content":"\u6d4b\u8bd5"}}
         * time : 1516697950
         */

        private String dialog_id;
        private String msg;
        private String time;

        public void setDialog_id(String dialog_id) {
            this.dialog_id = dialog_id;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getDialog_id() {
            return dialog_id;
        }

        public String getMsg() {
            return msg;
        }

        public String getTime() {
            return time;
        }
    }
}
