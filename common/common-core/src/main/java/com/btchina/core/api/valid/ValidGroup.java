package com.btchina.core.api.valid;

import jakarta.validation.groups.Default;

public interface ValidGroup extends Default {

    interface Create extends ValidGroup {

    }

    interface Update extends ValidGroup {

    }

    interface Query extends ValidGroup {

    }

    interface Delete extends ValidGroup {

    }
}
