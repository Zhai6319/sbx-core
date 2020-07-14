package com.sbx.core.secure.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sbx.core.model.api.Response;
import com.sbx.core.security.AuthUser;
import com.sbx.core.model.enums.EResultCode;
import com.sbx.core.security.utils.AuthUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Objects;

/**
 * <p>JwtVerifyFilter class:</p>
 * <p>自定义检验token过滤器</p>
 *
 * @author zhaijianchao
 * @version 1.0.0
 * @date 2020/4/6
 */
public class JwtVerifyFilter extends BasicAuthenticationFilter {

    public JwtVerifyFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    public JwtVerifyFilter(AuthenticationManager authenticationManager, AuthenticationEntryPoint authenticationEntryPoint) {
        super(authenticationManager, authenticationEntryPoint);
    }

    /**
     * 过滤请求
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain chain) {
        try {
            AuthUser authUser = AuthUtil.getUser();
            if (Objects.isNull(authUser)) {
                chain.doFilter(request,response);
                responseJson(response);
                return;
            }
            //获取权限失败，会抛出异常
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(authUser, null, AuthorityUtils.commaSeparatedStringToAuthorityList(authUser.getRoleName()));
            //获取后，将Authentication写入SecurityContextHolder中供后序使用
            SecurityContextHolder.getContext().setAuthentication(authentication);
            chain.doFilter(request, response);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            responseJson(response);
            return;
        }
    }

    /**
     * 未登录提示
     *
     * @param response
     */
    private void responseJson(HttpServletResponse response) {
        try {
            //未登录提示
            response.setContentType("application/json;charset=utf-8");
            PrintWriter out = response.getWriter();
            out.write(new ObjectMapper().writeValueAsString(Response.fail(EResultCode.REQ_REJECT)));
            out.flush();
            out.close();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }
}
