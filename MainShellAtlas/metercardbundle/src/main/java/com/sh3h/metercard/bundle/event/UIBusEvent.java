package com.sh3h.metercard.bundle.event;



public class UIBusEvent {

    public static class InitResult {
        private boolean isSuccess;

        public InitResult(boolean isSuccess) {
            this.isSuccess = isSuccess;
        }

        public boolean isSuccess() {
            return isSuccess;
        }

        public void setSuccess(boolean success) {
            isSuccess = success;
        }
    }
}
