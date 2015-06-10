/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package consumer;

import java.text.DecimalFormat;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.component.jms.JmsComponent;

public class ConsumerDriver {

    public static void main(String args[]) throws Exception {
    	
    	final Stock MSFTstock = new Stock("MSFT");
    	final Stock ORCLstock = new Stock("ORCL");
    	final Stock IBMstock = new Stock("IBM");
    	
    	final DecimalFormat mean_df = new DecimalFormat("###.##");
    	final DecimalFormat variance_df = new DecimalFormat("##.#######");
    	
        // create CamelContext
        CamelContext context = new DefaultCamelContext();

        // connect to ActiveMQ JMS broker listening on localhost on port 61616
        ConnectionFactory connectionFactory = 
        	new ActiveMQConnectionFactory("tcp://localhost:61616");
        context.addComponent("jms",
            JmsComponent.jmsComponentAutoAcknowledge(connectionFactory));
        
        // add our route to the CamelContext
        context.addRoutes(new RouteBuilder() {
            public void configure() {
                from("jms:queue:MPCS_51050_Stock_Data")
                	.log("RECEIVED:  jms queue: ${body} from file: ${header.CamelFileNameOnly}")
                    .choice()
                	.when(body().regex(".*MSFT.*"))
                		.process(new Processor(){

							@Override
							public void process(Exchange e) throws Exception {
								MSFTstock.addTicMsg(e.getIn().getBody(String.class));
								
								StringBuilder msgBody = new StringBuilder();
								msgBody.append(MSFTstock.stockName + "\t");
								msgBody.append(mean_df.format(MSFTstock.getStatistics(new BidPriceMean()))+"\t");
								msgBody.append(variance_df.format(MSFTstock.getStatistics(new BidPriceVariance()))+"\t");
								msgBody.append(variance_df.format(MSFTstock.getStatistics(new BidPriceStdDeviation()))+"\t");
								msgBody.append(mean_df.format(MSFTstock.getStatistics(new AskPriceMean()))+"\t");
								msgBody.append(variance_df.format(MSFTstock.getStatistics(new AskPriceVariance()))+"\t");
								msgBody.append(variance_df.format(MSFTstock.getStatistics(new AskPriceStdDeviation()))+"\t");
								
								e.getIn().setBody(msgBody);
							}
                			
                		})
                		.to("jms:topic:MPCS_51050_STAT_TOPIC_MSFT")
                	.when(body().regex(".*ORCL.*"))
                	    .process(new Processor(){
							@Override
							public void process(Exchange e) throws Exception {
								ORCLstock.addTicMsg(e.getIn().getBody(String.class));
								
								StringBuilder msgBody = new StringBuilder();
								msgBody.append(ORCLstock.stockName + "\t");
								msgBody.append(mean_df.format(ORCLstock.getStatistics(new BidPriceMean()))+"\t");
								msgBody.append(variance_df.format(ORCLstock.getStatistics(new BidPriceVariance()))+"\t");
								msgBody.append(variance_df.format(ORCLstock.getStatistics(new BidPriceStdDeviation()))+"\t");
								msgBody.append(mean_df.format(ORCLstock.getStatistics(new AskPriceMean()))+"\t");
								msgBody.append(variance_df.format(ORCLstock.getStatistics(new AskPriceVariance()))+"\t");
								msgBody.append(variance_df.format(ORCLstock.getStatistics(new AskPriceStdDeviation()))+"\t");
								
								e.getIn().setBody(msgBody);
							}
                		})
                		.to("jms:topic:MPCS_51050_STAT_TOPIC_ORCL")
                	.when(body().regex(".*IBM.*"))
                	    .process(new Processor(){
							@Override
							public void process(Exchange e) throws Exception {
								IBMstock.addTicMsg(e.getIn().getBody(String.class));
								
								StringBuilder msgBody = new StringBuilder();
								msgBody.append(IBMstock.stockName + "\t");
								msgBody.append(mean_df.format(IBMstock.getStatistics(new BidPriceMean()))+"\t");
								msgBody.append(variance_df.format(IBMstock.getStatistics(new BidPriceVariance()))+"\t");
								msgBody.append(variance_df.format(IBMstock.getStatistics(new BidPriceStdDeviation()))+"\t");
								msgBody.append(mean_df.format(IBMstock.getStatistics(new AskPriceMean()))+"\t");
								msgBody.append(variance_df.format(IBMstock.getStatistics(new AskPriceVariance()))+"\t");
								msgBody.append(variance_df.format(IBMstock.getStatistics(new AskPriceStdDeviation()))+"\t");
								
								e.getIn().setBody(msgBody);
							}
                			
                		})
                		.to("jms:topic:MPCS_51050_STAT_TOPIC_IBM");
            }
        });

        // start the route and let it do its work
        context.start();
        Thread.sleep(100000);

        // stop the CamelContext
        context.stop();
    }
}