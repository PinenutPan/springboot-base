package com.sinosoft.cms.base.utils;
import com.alibaba.druid.support.json.JSONUtils;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sinosoft.cms.base.bean.Base;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.io.*;
import java.math.BigDecimal;
import java.sql.*;
import java.util.Date;
import java.util.*;

/**
 * 自动生成文件
 */
@Slf4j
public class MybatisMapperCreator {
    private static final String MODULE = "cms";
    private static final String PACKAGE = "cms";

    private String[] executeTables = new String[]{"dw_sql_info", "dw_sql_param"};


    private final String user = "root";
    private final String password = "root";
    private final String url = "jdbc:mysql://127.0.0.1:3306/dc_cockpit?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC&autoReconnect=true&useSSL=false&zeroDateTimeBehavior=convertToNull";
    private final String driverName = "com.mysql.cj.jdbc.Driver";


    private static final String MAPPER_PACKAGE = getMapperPackage();
    private static final String BEAN_PACKAGE = getBeanPackage();
    private static final String SERVICE_PACKAGE = getServicePackage();
    private static final String SERVICE_IMPL_PACKAGE = getServiceImplPackage();
    private static final String BASE_PACKAGE = getCommonBasePackage();

    public static void main(String[] args) throws Exception {
        log.info("getBasePath:{}" + getBasePath());
        log.info("getCommonPath:{}" + getCommonPath());
        log.info("getWebResourcePath:{}" + getWebResourcePath());
        log.info("getMapperPackage:{}" + getMapperPackage());
        log.info("getServiceImplPackage:{}" + getServiceImplPackage());
        log.info("getServicePackage:{}" + getServicePackage());
        new MybatisMapperCreator().generate();
    }


    private static Map<String, Class> typeClass = new HashMap<>();

    static {
        typeClass.put("char", String.class);
        typeClass.put("varchar", String.class);
        typeClass.put("text", String.class);
        typeClass.put("longtext", String.class);
        typeClass.put("blob", String.class);

        typeClass.put("date", Date.class);
        typeClass.put("timestamp", Date.class);
        typeClass.put("datetime", Date.class);

        typeClass.put("int", Integer.class);
        typeClass.put("bigint", Long.class);
        typeClass.put("tinyint", Integer.class);
        typeClass.put("double", Double.class);

        typeClass.put("float", Float.class);

        typeClass.put("decimal", BigDecimal.class);

        typeClass.put("bool", Boolean.class);
        typeClass.put("boolean", Boolean.class);
        typeClass.put("bit", Boolean.class);

        typeClass.put("binary", Byte[].class);
    }

    private String tableName = null;

    private String beanName = null;

    private String mapperName = null;

    private String serviceName = null;

    private String serviceImplName = null;

    private Connection conn = null;

    private void init() throws ClassNotFoundException, SQLException {
        Class.forName(driverName);
        conn = DriverManager.getConnection(url, user, password);
    }

    /**
     * 获取所有的表
     *
     * @return
     * @throws SQLException
     */
    private List<String> getTables() throws SQLException {
        List<String> tables = new ArrayList<String>();
        PreparedStatement pstate = conn.prepareStatement("show tables");
        ResultSet results = pstate.executeQuery();
        while (results.next()) {
            String tableName = results.getString(1);
            for (String tempTable : executeTables) {
                if (tempTable.equals(tableName)) {
                    tables.add(tableName);
                    break;
                }
            }
//            if (tableName.startsWith("dw_")){
//                tables.add(tableName);
//            }
        }
        log.info(JSONUtils.toJSONString(tables));
        return tables;
    }

    private void processTable(String table) {
        StringBuffer sb = new StringBuffer(table.length());
        String tableNew = table.toLowerCase();
        String[] tables = tableNew.split("_");
        String temp = null;
        for (int i = 1; i < tables.length; i++) {
            temp = tables[i].trim();
            sb.append(temp.substring(0, 1).toUpperCase()).append(temp.substring(1));
        }
        beanName = sb.toString();
        mapperName = beanName + "Mapper";
        serviceName = beanName + "Service";
        serviceImplName = serviceName + "Impl";
    }

    /**
     * 首字母转小写
     *
     * @return
     */
    private String shotFirst(String str) {
        char[] chars = new char[1];
        chars[0] = str.charAt(0);
        String temp = new String(chars);
        if (chars[0] >= 'A' && chars[0] <= 'Z') {
            str = str.replaceFirst(temp, temp.toLowerCase());
        }
        return str;
    }

    private String processType(String type) {
        if (type.contains("(")) {
            type = type.substring(0, type.indexOf("("));
        }
        Class aClass = typeClass.get(type);
        if (null != aClass) {
            //System.out.println("type【"+type+ "】 match:" + aClass.getName());
            return aClass.getName();
        } else {
            System.err.println("表字段类型【" + type + "】找不到对应类型对应");
            throw new RuntimeException("表字段类型【" + type + "】找不到对应类型对应");
        }
    }

    private String processField(String field) {
        StringBuffer sb = new StringBuffer(field.length());
        String[] fields = field.split("_");
        String temp = null;
        sb.append(fields[0]);
        for (int i = 1; i < fields.length; i++) {
            temp = fields[i].trim();
            sb.append(temp.substring(0, 1).toUpperCase()).append(temp.substring(1));
        }
        return sb.toString();
    }

    /**
     * 将实体类名首字母改为小写
     *
     * @param beanName
     * @return
     */
    private String processResultMapId(String beanName) {
        return beanName.substring(0, 1).toLowerCase() + beanName.substring(1);
    }

    /**
     * 构建类上面的注释
     *
     * @param bw
     * @param text
     * @return
     * @throws IOException
     */
    private BufferedWriter buildClassComment(BufferedWriter bw, String text) throws IOException {
        bw.newLine();
        bw.newLine();
        bw.write("/**");
        bw.newLine();
        bw.write(" * ");
        bw.newLine();
        bw.write(" * " + text);
        bw.newLine();
        bw.write(" * ");
        bw.newLine();
        bw.write(" **/");
        return bw;
    }

    /**
     * 生成实体类
     *
     * @param columns
     * @param types
     * @param comments
     * @throws IOException
     */
    private void buildEntityBean(String table, List<String> columns, List<String> types, List<String> comments, String tableComment)
            throws IOException {
        File beanFile = new File(getCommonDomainPath(), beanName + ".java");
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(beanFile), "utf-8"));
        bw.write("package " + BEAN_PACKAGE + ";");
        bw.newLine();
        bw.write("import " + Base.class.getName() + ";");
        bw.newLine();
        bw.write("import " + IdType.class.getName() + ";");
        bw.newLine();
        bw.write("import " + TableName.class.getName() + ";");
        bw.newLine();
        bw.write("import " + TableId.class.getName() + ";");
        bw.newLine();
        bw.write("import " + ApiModel.class.getName() + ";");
        bw.newLine();
        bw.write("import " + Setter.class.getName() + ";");
        bw.newLine();
        bw.write("import " + Getter.class.getName() + ";");
        bw.newLine();
        bw.write("import " + ApiModelProperty.class.getName() + ";");
        bw.newLine();
        bw.write("import java.io.Serializable;");
        bw.newLine();
        bw.newLine();
        if (StringUtils.isEmpty(tableComment)) {
            tableComment = beanName;
        }
        bw.write("@Setter");
        bw.newLine();
        bw.write("@Getter");
        bw.newLine();
        bw.write("@ApiModel(\"" + tableComment + "\")");
        bw.newLine();
        bw.write("@TableName(\"" + table + "\")");
        bw.newLine();
        bw.write("public class " + beanName + " extends Base implements Serializable {");
        bw.newLine();
        bw.newLine();
        int size = columns.size();
        for (int i = 0; i < size; i++) {
            String desp = comments.get(i);
            if (StringUtils.isEmpty(desp)) {
                desp = processField(columns.get(i));
            }
            bw.write("\t@ApiModelProperty(\"" + desp + "\")");
            bw.newLine();
            if (columns.get(i).equals("id")) {
                bw.write("\t@TableId(value = \"id\",type = IdType.AUTO)");
                bw.newLine();
            }
            bw.write("\tprivate " + processType(types.get(i)) + " " + processField(columns.get(i)) + ";");
            bw.newLine();
            bw.newLine();
        }
        bw.newLine();
        bw.write("}");
        bw.newLine();
        bw.flush();
        bw.close();
    }


    /**
     * 构建Service文件
     *
     * @throws IOException
     */
    private void buildService() throws IOException {
        File serviceFile = new File(getCommonServicePath(), serviceName + ".java");
        if (serviceFile.exists()) {
            log.info(serviceName + ".java" + " exist ignore");
            return;
        }
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(serviceFile), "utf-8"));
        bw.write("package " + SERVICE_PACKAGE + ";");
        bw.newLine();
        bw.newLine();
        bw.write("import " + IService.class.getName() + ";");
        bw.newLine();
        bw.write("import " + BEAN_PACKAGE + "." + beanName + ";");
        bw.newLine();
        bw = buildClassComment(bw, serviceName);
        bw.newLine();
        bw.newLine();
        bw.write("public interface " + serviceName + " extends IService<" + beanName + ">{");
        bw.newLine();
        bw.write("}");
        bw.flush();
        bw.close();
    }

    /**
     * 构建ServiceImpl文件
     *
     * @throws IOException
     */
    private void buildServiceImpl() throws IOException {
        File daoFile = new File(getServiceImplPath(), serviceImplName + ".java");
        if (daoFile.exists()) {
            log.info(serviceImplName + ".java" + " exist ignore");
            return;
        }
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(daoFile), "utf-8"));
        bw.write("package " + SERVICE_IMPL_PACKAGE + ";");
        bw.newLine();
        bw.newLine();
        bw.write("import " + BEAN_PACKAGE + "." + beanName + ";");
        bw.newLine();
        bw.write("import " + MAPPER_PACKAGE + "." + mapperName + ";");
        bw.newLine();
        bw.write("import " + ServiceImpl.class.getName() + ";");
        bw.newLine();
        bw.write("import " + SERVICE_PACKAGE + "." + serviceName + ";");
        bw.newLine();
        bw.write("import org.springframework.stereotype.Service;");
        bw.newLine();
        bw = buildClassComment(bw, serviceName);
        bw.newLine();
        bw.write("@Service");
        bw.newLine();
        bw.write("public class " + serviceImplName + " extends ServiceImpl<" + mapperName + ", " + beanName
                + "> implements " + serviceName + "{");
        bw.newLine();
        bw.newLine();
        bw.write("}");
        bw.flush();
        bw.close();
    }

    /**
     * 构建Mapper文件
     *
     * @throws IOException
     */
    private void buildMapper() throws IOException {
        File mapperFile = new File(getServiceMapperPath(), mapperName + ".java");
        if (mapperFile.exists()) {
            log.info(mapperName + ".java" + " exist ignore");
            return;
        }
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(mapperFile), "utf-8"));
        bw.write("package " + MAPPER_PACKAGE + ";");
        bw.newLine();
        bw.newLine();
        bw.write("import com.baomidou.mybatisplus.core.mapper.BaseMapper;");
        bw.newLine();
        bw.write("import org.apache.ibatis.annotations.Mapper;");
        bw.newLine();
        bw.write("import " + BEAN_PACKAGE + "." + beanName + ";");
        bw.newLine();
        bw = buildClassComment(bw, mapperName + "数据库操作接口类");
        bw.newLine();
        bw.write("@Mapper");
        bw.newLine();
        bw.write("public interface " + mapperName + " extends BaseMapper<" + beanName + ">{");
        bw.newLine();
        bw.write("}");
        bw.flush();
        bw.close();
    }

    /**
     * 构建实体类映射XML文件
     *
     * @param columns
     * @param types
     * @param comments
     * @throws IOException
     */
    private void buildMapperXml(List<String> columns, List<String> types, List<String> comments) throws IOException {
        File mapperXmlFile = new File(getWebMapperPath(), mapperName + ".xml");
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(mapperXmlFile), "utf-8"));
        bw.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        bw.newLine();
        bw.write("<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" ");
        bw.newLine();
        bw.write("    \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">");
        bw.newLine();
        bw.write("<mapper namespace=\"" + MAPPER_PACKAGE + "." + mapperName + "\">");
        bw.newLine();
        bw.newLine();

        bw.write("</mapper>");
        bw.flush();
        bw.close();
    }

    /**
     * 获取所有的数据库表注释
     *
     * @return
     * @throws SQLException
     */
    private Map<String, String> getTableComment() throws SQLException {
        Map<String, String> maps = new HashMap<String, String>();
        PreparedStatement pstate = conn.prepareStatement("show table status");
        ResultSet results = pstate.executeQuery();
        while (results.next()) {
            String tableName = results.getString("NAME");
            String comment = results.getString("COMMENT");
            maps.put(tableName, comment);
        }
        return maps;
    }

    public void generate() throws ClassNotFoundException, SQLException, IOException {
        init();
        String prefix = "show full fields from ";
        List<String> columns = null;
        List<String> types = null;
        List<String> comments = null;
        PreparedStatement pstate = null;
        List<String> tables = getTables();
        Map<String, String> tableComments = getTableComment();

        File folder = new File(getWebResourcePath());
        if (!folder.exists()) {
            folder.mkdirs();
        }
        for (String table : tables) {
            columns = new ArrayList<String>();
            types = new ArrayList<String>();
            comments = new ArrayList<String>();
            pstate = conn.prepareStatement(prefix + table);
            ResultSet results = pstate.executeQuery();
            while (results.next()) {
                columns.add(results.getString("FIELD"));
                types.add(results.getString("TYPE"));
                comments.add(results.getString("COMMENT"));
            }
            tableName = table;
            processTable(table);
            String tableComment = tableComments.get(tableName);
            buildEntityBean(table, columns, types, comments, tableComment);
            buildMapper();
            buildMapperXml(columns, types, comments);
            buildService();
            buildServiceImpl();
        }
        conn.close();
    }

    public static String getCommonPath() {
        //取得根目录路径
        String relativePath = MybatisMapperCreator.class.getResource("./").getFile().toString();
        String currPackageName = relativePath.substring(relativePath.indexOf("com"));
        String commonPath = getBasePath() + MODULE + "/src/main/java/" + currPackageName.substring(0, currPackageName.indexOf(PACKAGE)) + PACKAGE + "/";
        File file = new File(commonPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        return commonPath;
    }

    public static String getCommonServicePath() {
        String commonServicePath = getCommonPath() + "service/";
        File file = new File(commonServicePath);
        if (!file.exists()) {
            file.mkdirs();
        }
        return commonServicePath;
    }

    public static String getCommonDomainPath() {
        String commonDomainPath = getCommonPath() + "domain/";
        File file = new File(commonDomainPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        return commonDomainPath;
    }

    public static String getServicePath() {
        //取得根目录路径
        String relativePath = MybatisMapperCreator.class.getResource("./").getFile().toString();
        String currPackageName = relativePath.substring(relativePath.indexOf("com"));
        String servicePath = getBasePath() + MODULE + "/src/main/java/" + currPackageName.substring(0, currPackageName.indexOf(PACKAGE)) + PACKAGE + "/service/";
        File file = new File(servicePath);
        if (!file.exists()) {
            file.mkdirs();
        }
        return servicePath;
    }

    public static String getServiceMapperPath() {
        String serviceMapperPath = getCommonPath() + "mapper/";
        File file = new File(serviceMapperPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        return serviceMapperPath;
    }

    public static String getMapperPackage() {
        //取得根目录路径
        String relativePath = MybatisMapperCreator.class.getResource("./").getFile().toString();
        String currPackageName = relativePath.substring(relativePath.indexOf("com"));
        String mapperPackage = (currPackageName.substring(0, currPackageName.indexOf(PACKAGE)) + PACKAGE + "/mapper").replace("/", ".");
        return mapperPackage;
    }

    public static String getBeanPackage() {
        //取得根目录路径
        String relativePath = MybatisMapperCreator.class.getResource("./").getFile().toString();
        String currPackageName = relativePath.substring(relativePath.indexOf("com"));
        String beanPackage = (currPackageName.substring(0, currPackageName.indexOf(PACKAGE)) + PACKAGE + "/domain").replace("/", ".");
        return beanPackage;
    }

    public static String getCommonBasePackage() {
        //取得根目录路径
        String relativePath = MybatisMapperCreator.class.getResource("./").getFile().toString();
        String currPackageName = relativePath.substring(relativePath.indexOf("com"));
        String beanPackage = (currPackageName.substring(0, currPackageName.indexOf(PACKAGE)) + "/base").replace("/", ".");
        return beanPackage;
    }

    public static String getServicePackage() {
        //取得根目录路径
        String relativePath = MybatisMapperCreator.class.getResource("./").getFile().toString();
        String currPackageName = relativePath.substring(relativePath.indexOf("com"));
        String servicePackage = (currPackageName.substring(0, currPackageName.indexOf(PACKAGE)) + PACKAGE + "/service").replace("/", ".");
        return servicePackage;
    }

    public static String getServiceImplPackage() {
        //取得根目录路径
        String relativePath = MybatisMapperCreator.class.getResource("./").getFile().toString();
        String currPackageName = relativePath.substring(relativePath.indexOf("com"));
        String serviceImplPackage = (currPackageName.substring(0, currPackageName.indexOf(PACKAGE)) + PACKAGE + "/service/impl").replace("/", ".");
        return serviceImplPackage;
    }


    public static String getServiceImplPath() {
        String serviceImplPath = getServicePath() + "/impl/";
        File file = new File(serviceImplPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        return serviceImplPath;
    }

    public static String getWebResourcePath() {
        //取得根目录路径
        String relativePath = MybatisMapperCreator.class.getResource("./").getFile().toString();
        String currPackageName = relativePath.substring(relativePath.indexOf("com"));
        String webResourcePath = getBasePath() + MODULE + "/src/main/resources/";
        File file = new File(webResourcePath);
        if (!file.exists()) {
            file.mkdirs();
        }
        return webResourcePath;
    }

    public static String getWebMapperPath() {
        //取得根目录路径
        String webResourcePath = getBasePath() + MODULE + "/src/main/resources/mapper/" + PACKAGE + "/";
        File file = new File(webResourcePath);
        if (!file.exists()) {
            file.mkdirs();
        }
        return webResourcePath;
    }


    public static String getBasePath() {
        //取得根目录路径
        String rootPath = MybatisMapperCreator.class.getResource("/").getFile().toString();
        return rootPath.substring(0, rootPath.indexOf(MODULE));
    }
}
