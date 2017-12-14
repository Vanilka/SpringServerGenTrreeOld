package mobile.client.gentree.gentreemobile.rest.responses;

import gentree.exception.ExceptionBean;

/**
 * Created by vanilka on 14/12/2017.
 */
public class ExceptionResponse extends ServerResponse {


        private ExceptionBean exception;

        public ExceptionResponse() {
            this(null);
        }

        public ExceptionResponse(ExceptionBean exception) {
            super(ResponseStatus.FAIL);
            this.exception = exception;
        }

        public ExceptionBean getException() {
            return exception;
        }

        public void setException(ExceptionBean exception) {
            this.exception = exception;
        }

        @Override
        public String toString() {
            return "ExceptionResponse{" +
                    "exception=" + exception +
                    "} " + super.toString();
        }
    }

