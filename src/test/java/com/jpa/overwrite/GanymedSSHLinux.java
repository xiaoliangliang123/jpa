package com.jpa.overwrite;

import java.io.*;

//import org.apache.commons.lang.StringUtils;

import ch.ethz.ssh2.ChannelCondition;
import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;
import org.apache.tomcat.util.http.fileupload.IOUtils;

public class GanymedSSHLinux {
    private static String DEFAULTCHART = "UTF-8";
    private String line;

    private static Connection login(String ip, String username, String password) {
        boolean flag = false;
        Connection connection = null;
        try {
            connection = new Connection(ip);
            connection.connect();// 连接
            flag = connection.authenticateWithPassword(username, password);// 认证
            if (flag) {
                System.out.println("================登录成功==================");
                return connection;
            }
        } catch (IOException e) {
            System.out.println("=========登录失败=========" + e);
            connection.close();
        }
        return connection;
    }

    /**
     * 远程执行shll脚本或者命令
     *
     *            即将执行的命令
     * @return 命令执行完后返回的结果值
     */
    public static void execmd(Connection conn,String cmds) throws Exception {
        InputStream stdOut = null;
        InputStream stdErr = null;
        String outStr = "";
        String outErr = "";
        int ret = -1;
        try {
                Session session = conn.openSession();
                // 建立虚拟终端
                session.requestPTY("bash");
                // 打开一个Shell
                session.startShell();
                stdOut = new StreamGobbler(session.getStdout());
                stdErr = new StreamGobbler(session.getStderr());
                BufferedReader stdoutReader = new BufferedReader(new InputStreamReader(stdOut));
                BufferedReader stderrReader = new BufferedReader(new InputStreamReader(stdErr));

                // 准备输入命令
                PrintWriter out = new PrintWriter(session.getStdin());
                // 输入待执行命令
                out.println(cmds);
                out.println("cd /home");
                out.println("ls");

                out.println("exit");
                // 6. 关闭输入流
                out.close();
                // 7. 等待，除非1.连接关闭；2.输出数据传送完毕；3.进程状态为退出；4.超时
                session.waitForCondition(ChannelCondition.CLOSED | ChannelCondition.EOF | ChannelCondition.EXIT_STATUS , 30000);
                System.out.println("Here is the output from stdout:");
            while (true)
            {
                String line = stdoutReader.readLine();
                if (line == null)
                    break;
                System.out.println(line);
            }
                //org.apache.logging.log4j.core.util.IOUtils.toString(stdoutReader);
                System.out.println("Here is the output from stderr:");
            while (true)
            {
                String line = stderrReader.readLine();
                if (line == null)
                    break;
                System.out.println(line);
            }
                /* Show exit status, if available (otherwise "null") */
                System.out.println("ExitCode: " + session.getExitStatus());
                ret = session.getExitStatus();
                session.close();/* Close this session */

        } finally {
            if (conn != null) {
                conn.close();
            }
            IOUtils.closeQuietly(stdOut);
            IOUtils.closeQuietly(stdErr);
        }
    }
    /**
     * 解析脚本执行返回的结果集
     *
     * @param in
     *            输入流对象
     * @param charset
     *            编码
     * @return 以纯文本的格式返回
     */
    private static String processStdout(InputStream in, String charset) {
        InputStream stdout = new StreamGobbler(in);
        StringBuffer buffer = new StringBuffer();
        ;
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(stdout, charset));
            String line = null;
            while ((line = br.readLine()) != null) {
                buffer.append(line + "\n");
                System.out.println(line);
            }
            br.close();
        } catch (UnsupportedEncodingException e) {
            System.out.println("解析脚本出错：" + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("解析脚本出错：" + e.getMessage());
            e.printStackTrace();
        }
        return buffer.toString();
    }

    public static void main(String[] args) throws Exception {
        long currentTimeMillis = System.currentTimeMillis();
        String host = "154.8.162.93";
        String user = "root";
        String password = "Madness1111";
        String cmd = "uname -a";
        Connection connection = login(host, user, password);
        execmd(connection, cmd);
        cmd = "cd /home";
        execmd(connection, cmd);
        cmd = "ls";
        execmd(connection, cmd);
        long currentTimeMillis1 = System.currentTimeMillis();
        System.out.println("ganymed-ssh2方式"+(currentTimeMillis1-currentTimeMillis));
    }
}