package cn.pers.travel.web.servlet;

import cn.pers.travel.domain.ResultInfo;
import cn.pers.travel.domain.User;
import cn.pers.travel.service.UserService;
import cn.pers.travel.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/user/*")
public class UserServlet extends BaseServlet {

    //声明UserService业务对象
    private UserService service = new UserServiceImpl();

    /**
     * 注册功能
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void register(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //验证校验
        String check = request.getParameter("check");
        //从session中获取验证码
        HttpSession session = request.getSession();
        String checkcode_server = (String) session.getAttribute("CHECKCODE_SERVER");
        session.removeAttribute("CHECKCODE_SERVER");//为了保证验证码只能使用一次
        //比较
        if (checkcode_server == null || !checkcode_server.equalsIgnoreCase(check)) {
            //验证码错误
            ResultInfo info = new ResultInfo();
            //注册失败
            info.setFlag(false);
            info.setErrorMsg("验证码错误");
            //将info对象序列化为json
            /*ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(info);*/
            String json = writeValueAsString(info, response);
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(json);
            return;
        }

        //1、获取数据
        Map<String, String[]> map = request.getParameterMap();
        //2、封装对象
        User user = new User();
        try {
            BeanUtils.populate(user, map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        //3、调用service完成注册
        //UserService service = new UserServiceImpl();
        boolean flag = service.register(user);
        ResultInfo info = new ResultInfo();

        //4、响应结果
        if (flag) {
            //注册成功
            info.setFlag(true);
        } else {
            //注册失败
            info.setFlag(false);
            info.setErrorMsg("注册失败!");
        }
        session.setAttribute("user", user);

        //将info对象序列化为json
        /*ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(info);*/
        String json = writeValueAsString(info, response);

        //将json数据写回客户端
        //设置content-type
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(json);
    }

    /**
     * 登录功能
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1、获取用户名和密码数据
        Map<String, String[]> map = request.getParameterMap();
        //2、封装User对象
        User user = new User();
        try {
            BeanUtils.populate(user, map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        //3、调用service查询
        //UserService service = new UserServiceImpl();
        User u = service.login(user);
        ResultInfo info = new ResultInfo();

        //4、判断用户对象是否为null
        if (u == null) {
            //用户名密码错误
            info.setFlag(false);
            info.setErrorMsg("用户名或密码错误");
        }
        //5、判断用户是否激活
        if (u != null && !"Y".equals(u.getStatus())) {
            //用户尚未激活
            info.setFlag(false);
            info.setErrorMsg("您尚未激活，请登录邮件激活");
        }
        //6、判断登录成功
        if (u != null && "Y".equals(u.getStatus())) {
            info.setFlag(true);
        }

        //响应数据,序列化
        /*ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        mapper.writeValue(response.getOutputStream(), info);*/
        writeValue(info, response);

        //校验验证码
        String check = request.getParameter("check");
        //从session中获取验证码
        HttpSession session = request.getSession();
        String checkcode_server = (String) session.getAttribute("CHECKCODE_SERVER");
        session.removeAttribute("CHECKCODE_SERVER");//为了保证验证码只能使用一次
        //比较
        if (checkcode_server == null || !checkcode_server.equalsIgnoreCase(check)) {
            //验证码错误
            //登录失败
            info.setFlag(false);
            info.setErrorMsg("验证码错误");
            return;
        }
        session.setAttribute("user", u);
    }

    /**
     * 查询单个对象
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void findOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //从session中获取登录用户
        Object user = request.getSession().getAttribute("user");
        //将user写回客户端
        /*ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        mapper.writeValue(response.getOutputStream(), user);*/
        writeValue(user, response);
    }

    /**
     * 退出功能
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void exit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1、销毁session
        request.getSession().invalidate();

        //2、跳转登录页面
        response.sendRedirect(request.getContextPath() + "/login.html");
    }

    /**
     * 激活功能
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void active(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1、获取激活码
        String code = request.getParameter("code");
        if (code != null) {
            //2、调用service完成激活
            //UserService service = new UserServiceImpl();
            boolean flag = service.active(code);

            //3、判断标记
            String msg = null;
            if (flag) {
                //激活成功
                msg = "激活成功，请<a href = 'http://localhost/travel/login.html'>登录</a>";
            } else {
                //激活失败
                msg = "激活失败";
            }

            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(msg);
        }
    }
}