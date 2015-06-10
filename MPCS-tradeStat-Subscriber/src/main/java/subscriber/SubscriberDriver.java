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
package subscriber;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.component.jms.JmsComponent;

public class SubscriberDriver {

    public static void main(String args[]) throws Exception {
        // connect to ActiveMQ JMS broker listening on localhost on port 61616
        ConnectionFactory connectionFactory = 
        	new ActiveMQConnectionFactory("tcp://localhost:61616");
    	
    	//Tokyo portfolio
    	Portfolio rootPortfolioTokyo = new Portfolio("Tokyo_rootPortfolio");
    	Portfolio MSFTportfolioTokyo = new Portfolio("MSFT_Tokyo");
    	Portfolio ORCLportfolioTokyo = new Portfolio("ORCL_Tokyo");
    	
    	StockStatInfo MSFTtokyo_bidMean = new StockStatInfo("MSFT","bidMean");
    	StockStatInfo MSFTtokyo_bidStdDev = new StockStatInfo("MSFT","bidStdDev");
    	
    	StockStatInfo ORCLtokyo_askMean = new StockStatInfo("ORCL","askMean");
    	StockStatInfo ORCLtokyo_askStdDev = new StockStatInfo("ORCL","askStdDev");
    	
    	MSFTportfolioTokyo.add(MSFTtokyo_bidMean);
    	MSFTportfolioTokyo.add(MSFTtokyo_bidStdDev);
    	ORCLportfolioTokyo.add(ORCLtokyo_askMean);
    	ORCLportfolioTokyo.add(ORCLtokyo_askStdDev);
    	
    	rootPortfolioTokyo.add(MSFTportfolioTokyo);
    	rootPortfolioTokyo.add(ORCLportfolioTokyo);
    	
    	//London Portfolio
    	Portfolio rootPortfolioLondon = new Portfolio("London_rootPortfolio");
    	Portfolio bidMeanPortfolioLondon = new Portfolio("bidMean_London");
    	Portfolio askStdDevPortfolioLondon = new Portfolio("askStdDev_London");
    	
    	StockStatInfo MSFTLondon_bidMean = new StockStatInfo("MSFT","bidMean");
    	StockStatInfo IBMLondon_bidMean = new StockStatInfo("IBM","bidMean");
    	
    	StockStatInfo ORCLLondon_askStdDev = new StockStatInfo("ORCL","askStdDev");
    	StockStatInfo IBMLondon_askStdDev = new StockStatInfo("IBM","askStdDev");
    	
    	bidMeanPortfolioLondon.add(MSFTLondon_bidMean);
    	bidMeanPortfolioLondon.add(IBMLondon_bidMean);
    	askStdDevPortfolioLondon.add(ORCLLondon_askStdDev);
    	askStdDevPortfolioLondon.add(IBMLondon_askStdDev);
    	
    	rootPortfolioLondon.add(bidMeanPortfolioLondon);
    	rootPortfolioLondon.add(askStdDevPortfolioLondon);
    	
    	//NY
    	Portfolio rootPortfolioNewYork = new Portfolio("NewYork_rootPortfolio");
    	
    	Portfolio IBMportfolioNewYork = new Portfolio("IBM_NewYork");
    	
    	StockStatInfo IBMnewyork_bidMean = new StockStatInfo("IBM","bidMean");
    	StockStatInfo IBMnewyork_askMean = new StockStatInfo("IBM","askMean");
    	
    	IBMportfolioNewYork.add(IBMnewyork_bidMean);
    	IBMportfolioNewYork.add(IBMnewyork_askMean);
    	
    	rootPortfolioNewYork.add(IBMportfolioNewYork);
    	
    	TradingEngine tradingEngineTokyo = new TradingEngine("Tokyo_TradingEngine",rootPortfolioTokyo);
    	TradingEngine tradingEngineLondon = new TradingEngine("London_TradingEngine",rootPortfolioLondon);
    	TradingEngine tradingEngineNewYork = new TradingEngine("NewYork_TradingEngine",rootPortfolioNewYork);
    	
    	
        // create CamelContext
        CamelContext contextTokyo = new DefaultCamelContext();
        CamelContext contextLondon = new DefaultCamelContext();
        CamelContext contextNewYork = new DefaultCamelContext();
        
        // connect to ActiveMQ JMS broker listening on localhost on port 61616
        contextTokyo.addComponent("jms",
        	JmsComponent.jmsComponentAutoAcknowledge(connectionFactory));
        contextLondon.addComponent("jms",
        	JmsComponent.jmsComponentAutoAcknowledge(connectionFactory));
        contextNewYork.addComponent("jms",
            JmsComponent.jmsComponentAutoAcknowledge(connectionFactory));
        
        RouteBuilder tradingEngineChannelTokyo = new TradingEngineChannel(tradingEngineTokyo);
        RouteBuilder tradingEngineChannelLondon = new TradingEngineChannel(tradingEngineLondon);
        RouteBuilder tradingEngineChannelNewYork = new TradingEngineChannel(tradingEngineNewYork);
        
        contextTokyo.addRoutes(tradingEngineChannelTokyo);
        contextLondon.addRoutes(tradingEngineChannelLondon);
        contextNewYork.addRoutes(tradingEngineChannelNewYork);
        

        // start the route and let it do its work
        contextTokyo.start();
        contextLondon.start();
        contextNewYork.start();
        Thread.sleep(150000);

        // stop the CamelContext
        contextTokyo.stop();
        contextLondon.stop();
        contextNewYork.stop();
       
    }
}
    
    class TradingEngineChannel extends RouteBuilder{

    	TradingEngine tradingEngine;

    	public TradingEngineChannel(TradingEngine tradingEngine)
    	{
    		this.tradingEngine = tradingEngine;
    	}

    	public void configure() {
    		
    		from("jms:topic:MPCS_51050_STAT_TOPIC_MSFT")
    			.process(new Processor(){
    				public void process(Exchange e) throws Exception{	
    					String msg = e.getIn().getBody(String.class);
    					
    		    		tradingEngine.update(msg);
    				e.getIn().setBody(tradingEngine.report());
    				}
    			})
    			.to("jms:queue:TRADING_ENGINE_REPORT_" + tradingEngine.engineName);
    		
    		from("jms:topic:MPCS_51050_STAT_TOPIC_ORCL")
			.process(new Processor(){
				public void process(Exchange e) throws Exception{	
					String msg = e.getIn().getBody(String.class);
					
					tradingEngine.update(msg);
				e.getIn().setBody(tradingEngine.report());
				}
			})
			.to("jms:queue:TRADING_ENGINE_REPORT_" + tradingEngine.engineName);  
    		
    		from("jms:topic:MPCS_51050_STAT_TOPIC_IBM")
			.process(new Processor(){
				public void process(Exchange e) throws Exception{	
					String msg = e.getIn().getBody(String.class);
					
					tradingEngine.update(msg);
				e.getIn().setBody(tradingEngine.report());
				}
			})
			.to("jms:queue:TRADING_ENGINE_REPORT_" + tradingEngine.engineName);  
   
    	}
 }
