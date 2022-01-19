package io.dmitrikonnov.DeanerySimpleSpringBootApp.enums;

public enum LoggingMessage {
    /**
     * "some message: String %s and its value %d"
     * */
    SOME_MSG {

        public String args1(String arg1) {
            return String.format("some message: String %s", arg1);
        }
        public String args2(String arg1, int arg2) {
            return String.format("some msg %s and %d", arg1, arg2);
        }
    },

    SOME_OTHER_MSG {
        /**
         * "some message: String %s"
         *
         * @param arg1*/
        public String args1(String arg1) {
            return String.format("some message: String %s", arg1);
        }
        /**
         * "some msg %s has value %d
         * @param arg1 String
         * @param arg2 int*/
        public String args2(String arg1, int arg2) {
            return String.format("some msg %s has value %d", arg1,arg2);
        }
    };


    public abstract String args1(String arg1);
    public abstract String args2(String arg1, int arg2);



}
