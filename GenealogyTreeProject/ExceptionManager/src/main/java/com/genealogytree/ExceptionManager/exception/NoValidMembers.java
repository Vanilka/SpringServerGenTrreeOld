package com.genealogytree.ExceptionManager.exception;

import com.genealogytree.ExceptionManager.config.Causes;

/**
 * Created by Martyna SZYMKOWIAK on 23/03/2017.
 */
public class NoValidMembers extends Exception{


        public NoValidMembers() {
            this(Causes.NO_VALID_MEMBERS.toString());
        }

        public NoValidMembers(String message) {
            super(message);
        }

        public NoValidMembers(String message, Throwable cause) {
            super(message, cause);
        }

        public Causes getCausesInstance() {
            return Causes.NO_VALID_MEMBERS;
        }


}
