/*
 * Copyright (c) 2018, NARH https://github.com/NARH
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * * Redistributions of source code must retain the above copyright notice,
 *   this list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 * * Neither the name of the copyright holder nor the names of its contributors
 *   may be used to endorse or promote products derived from this software
 *   without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.github.narh.sample.swagger;

import java.util.ArrayList;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger の設定クラス
 *
 * @author ARH https://github.com/NARH
 *
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

  /**
   * Swagger2 準拠のドキュメンテーション作成プラグイン設定
   *
   * @return Docket Swagger-ui
   */
  @Bean
  public Docket docker() {
    return new Docket(DocumentationType.SWAGGER_2)
        .select()
        .apis(RequestHandlerSelectors.any())
        .paths(paths())
        .build().apiInfo(apiInfo());
  }

  /**
   * 仕様ドキュメンテーション対象URI列挙
   * @return
   */
  @SuppressWarnings("unchecked")
  private Predicate<String> paths() {
    return Predicates.and(
        Predicates.not(Predicates.containsPattern("/view"))   // /view  を含むURIは非対象
       ,Predicates.or(
          Predicates.containsPattern("/data/*")               // /data はAPI としてドキュメント対象
         ,Predicates.containsPattern("/authority/*")          // /authority は認証API としてドキュメント対象
         ,Predicates.containsPattern("/file/*")               // /file はファイル操作APIとしてドキュメント対象
       )
    );
  }

  /**
   * API 情報
   * @return
   */
  @SuppressWarnings("rawtypes")
  private ApiInfo apiInfo() {
    return new ApiInfo(
        "Swagger Sample API"                                     //title
       ,"Seagger Sample Description"                             //description
       ,"0.0.1.SNAPSHOT"                                         //version
       ,""                                                       //termsOfServiceUrl
       ,new Contact("NARH","https://github.com/NARH","")         //contact
       ,"https://github.com/NARH"                                //license
       ,""                                                       //licenseUrl
       , new ArrayList<VendorExtension>());
  }
}
