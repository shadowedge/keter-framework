package keter.web.routes;

/**
 * Defines default RESTful routes using in controllers. <code>
 *   Index -> /
 *   New -> /new
 *   Show -> /{id}
 *   Edit -> /{id}
 *   Destroy -> /{id}/destroy		destroy confirmation view
 * </code>
 * 
 * @author Dawid Fatyga
 * 
 */
/**
 * REST 是设计基于命名资源 — 
 * 例如，以 Uniform Resource Locators（URL）、Uniform Resource Identifiers（URI）和 Uniform Resource Names（URN）的形式 —
 * 而非消息的松耦合 Web 应用程序的一种风格。
 * REST 巧妙地借助已经验证过的成功的 Web 基础设施 — HTTP。
 * 换句话说，REST 利用了 HTTP 协议的某些方面，例如 GET 和 POST 请求。
 * 这些请求可以很好地映射到标准业务应用程序需求，诸如创建、读取、更新和删除
 * CRUD/HTTP 映射
	应用程序任务	HTTP 命令
	创建	POST
	读取	GET
	更新	PUT
	删除	DELETE
 */
public interface RestfulRoutes {
	static final String Index = "";
	static final String New = "/new";
	static final String Show = "/{id}";
	static final String Edit = Show;
	static final String Destroy = "/{id}/destroy";
	
	//带分页的请求
	static final String Pagin = "/topage/{paginExp}/";
}
