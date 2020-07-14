package com.sbx.core.secure.expression;

import com.sbx.core.security.AuthUser;
import com.sbx.core.tool.util.CollectionUtil;
import com.sbx.core.tool.util.StringUtil;
import com.sbx.core.security.utils.AuthUtil;
import com.sbx.core.tool.util.Func;
import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;

/**
 * <p>SbxSecurityExpressionOperations class:</p>
 *
 * @author zhaijianchao
 * @version 1.0.0
 * @date 2020/4/6
 */
public class SbxSecurityExpressionRoot extends SecurityExpressionRoot implements MethodSecurityExpressionOperations {
    private Object filterObject;
    private Object returnObject;
    private Object target;

    private String defaultRolePrefix = "SBX_ROLE";

    public SbxSecurityExpressionRoot(Authentication a) {
        super(a);
    }

    @Override
    public void setFilterObject(Object filterObject) {
        this.filterObject = filterObject;
    }

    @Override
    public Object getFilterObject() {
        return filterObject;
    }

    @Override
    public void setReturnObject(Object returnObject) {
        this.returnObject = returnObject;
    }

    @Override
    public Object getReturnObject() {
        return returnObject;
    }

    /**
     * Sets the "this" property for use in expressions. Typically this will be the "this"
     * property of the {@code JoinPoint} representing the method invocation which is being
     * protected.
     *
     * @param target the target object on which the method in is being invoked.
     */
    public void setThis(Object target) {
        this.target = target;
    }

    @Override
    public Object getThis() {
        return target;
    }

    public final boolean hasSbxAuthority(String authority) {
        return hasSbxAnyAuthority(authority);
    }

    public final boolean hasSbxAnyAuthority(String... authorities) {
        AuthUser user = AuthUtil.getUser();
        if (user == null) {
            return false;
        }
        String authority = user.getAuthority();
        if (StringUtil.isBlank(authority)) {
            return false;
        }
        String[] userAuthority = Func.toStrArray(authority);
        for (String r : authorities) {
            if (CollectionUtil.contains(userAuthority, r)) {
                return true;
            }
        }
        return false;
    }

    public final boolean hasSbxRole(String role) {
        return hasSbxAnyRole(role);
    }

    public final boolean hasSbxAnyRole(String... roles) {
        AuthUser user = AuthUtil.getUser();
        if (user == null) {
            return false;
        }
        String userRole = user.getRoleName();
        if (StringUtil.isBlank(userRole)) {
            return false;
        }
        String[] userRoles = Func.toStrArray(userRole);
        for (String r : roles) {
            if (CollectionUtil.contains(userRoles, r)) {
                return true;
            }
        }
        return false;
    }
}
