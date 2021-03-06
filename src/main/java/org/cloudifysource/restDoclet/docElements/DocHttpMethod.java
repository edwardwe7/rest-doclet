/*******************************************************************************
 * Copyright (c) 2012 GigaSpaces Technologies Ltd. All rights reserved
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package org.cloudifysource.restDoclet.docElements;

import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import static com.google.common.collect.Lists.newArrayList;

/**
 *
 * @author yael
 *
 */
public class DocHttpMethod {
	private final String methodSignatureName;
	private final String httpMethodName;
	private String description;

	private List<DocParameter> params;
	private DocReturnDetails returnDetails;

	private DocJsonRequestExample jsonRequestExample;
	private DocJsonResponseExample jsonResponseExample;
	private String requestExample;
	private String responseExample;
  private Iterable<String> docHeaders;
  private Collection<DocResponseStatus> responseStatuses_ = newArrayList();

  public DocHttpMethod(final String methodSignatureName, final String requestMethod) {
		this.methodSignatureName = methodSignatureName;
		this.httpMethodName = requestMethod;
	}

	public String getMethodSignatureName() {
		return methodSignatureName;
	}

	public String getHttpMethodName() {
		return httpMethodName;
	}

  public Iterable<String> getHeaders() {
    return docHeaders;
  }

	public String getDescription() {
		return description;
	}

	public void setDescription(final String commentText) {
		description = commentText;

	}

	public List<DocParameter> getParams() {
		return params;
	}

	/**
	 *
	 * @param params .
	 */
	public void setParams(final List<DocParameter> params) {
		this.params = params;
	}

	public List<DocParameter> getAnnotatedParams() {
		return params;
	}

	public DocReturnDetails getReturnDetails() {
		return returnDetails;
	}

	public void setReturnDetails(final DocReturnDetails returnDetails) {
		this.returnDetails = returnDetails;
	}

	public DocJsonResponseExample getJsonResponseExample() {
		return jsonResponseExample;
	}

	public void setJsonResponseExample(final DocJsonResponseExample jsonResponseExample) {
		this.jsonResponseExample = jsonResponseExample;
	}

	public DocJsonRequestExample getJsonRequestExample() {
		return jsonRequestExample;
	}

	public void setJsonRequestExample(final DocJsonRequestExample request) {
		this.jsonRequestExample = request;
	}

	public Collection<DocResponseStatus> getResponseStatuses() {
    return responseStatuses_;
	}

	public String getRequestExample() {
		return requestExample;
	}

	public void setRequestExample(final String requestExample) {
		this.requestExample = requestExample;
	}

	public String getResponseExample() {
		return responseExample;
	}

	public void setResponseExample(final String responseExample) {
		this.responseExample = responseExample;
	}

  public void setHeaders(Iterable<String> headers) {
    docHeaders = headers;
  }

  public void setResponseStatuses(Collection<DocResponseStatus> statuses) {
    responseStatuses_ = statuses;
  }

	@Override
	public String toString() {
		String httpMethodShort = httpMethodName.substring(httpMethodName
				.lastIndexOf('.') + 1);
		StringBuilder str = new StringBuilder("http method: ").append(httpMethodShort).append('\n');
		if (StringUtils.isBlank(description)) {
			str.append("description: ").append(description).append('\n');
		}
		if (params != null && !params.isEmpty()) {
			StringBuilder paramsStr = new StringBuilder();
			for (DocParameter param : params) {
				paramsStr.append("   ").append(param).append('\n');
			}
			str.append("parameters: \n").append(paramsStr);
		}
		str.append("returns ").append(returnDetails);
		if (jsonRequestExample != null) {
			str.append("Request example: ").append(jsonRequestExample).append('\n');
		} else {
			str.append("Request example: ").append(requestExample).append('\n');
		}
		if (jsonResponseExample != null) {
			str.append("Response example: ").append(jsonResponseExample).append('\n');
		} else {
			str.append("Response example: ").append(responseExample).append('\n');
		}

		return str.toString();
	}


}
