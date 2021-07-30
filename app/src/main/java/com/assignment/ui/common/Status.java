package com.assignment.ui.common;

import androidx.annotation.IntDef;

@IntDef({Status.ERROR, Status.SUCCESS})
public @interface Status {
    int SUCCESS = 1;
    int ERROR = 2;
}
