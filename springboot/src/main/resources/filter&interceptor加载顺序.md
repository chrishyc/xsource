##顺序
filter链路执行完,然后调用servlet,DispatcherServlet中的HandlerInterceptor链路执行
![](https://upload-images.jianshu.io/upload_images/1704592-93aadaf76ab4746b.jpg)
##
interceptor
```
protected void doDispatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
		        ...
				mappedHandler = getHandler(processedRequest);
				if (mappedHandler == null) {
					noHandlerFound(processedRequest, response);
					return;
				}

				
				String method = request.getMethod();
				...

				if (!mappedHandler.applyPreHandle(processedRequest, response)) {
					return;
				}

				// Actually invoke the handler.
				mv = ha.handle(processedRequest, response, mappedHandler.getHandler());
                ...

				applyDefaultViewName(processedRequest, mv);
				mappedHandler.applyPostHandle(processedRequest, response, mv);
			    ...
			    processDispatchResult(processedRequest, response, mappedHandler, mv, dispatchException);
	}
```
