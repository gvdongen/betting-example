/*
 * Copyright (c) 2024 - Restate Software, Inc., Restate GmbH
 *
 * This file is part of the Restate examples,
 * which is released under the MIT license.
 *
 * You can find a copy of the license in the file LICENSE
 * in the root directory of this repository or package or at
 * https://github.com/restatedev/examples/
 */

package org.example;

import dev.restate.sdk.http.vertx.RestateHttpEndpointBuilder;

public class AppMain {
  public static void main(String[] args) {
    RestateHttpEndpointBuilder.builder()
            .bind(new PaymentServiceImpl())
            .bind(new RGServiceImpl())
            .bind(new WalletServiceImpl())
            .bind(new CommsServiceImpl())
            .bind(new ExampleWorkflow())
            .buildAndListen();
  }
}
