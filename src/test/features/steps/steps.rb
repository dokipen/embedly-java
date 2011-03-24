require 'rubygems'
require 'json'
require 'rspec/expectations'
include_class 'com.embedly.api.Api'
include_class 'org.apache.commons.logging.LogFactory'

USER_AGENT = 'embedly-java-cucumber'

Given /^an embedly host( [^\s]+)?( with key)?$/ do |host, key_enabled|
  key = nil
  if key_enabled
    key = ENV['EMBEDLY_KEY']
    raise 'Please set env variable $EMBEDLY_KEY' unless key
  end
  Api.setLog(LogFactory.getLog('com.embedly.api.Api'))
  @api = Api.new(USER_AGENT, key, host)
end

When /(\w+) is called with the (.*) URLs?( and ([^\s]+) flag)?$/ do |method, urls, _, flag|
  @result = nil
  begin
    urls = urls.split(',')
    opts = {}
    if urls.size == 1
      opts['url'] = urls.first
    else
      opts['urls'] = urls.to_java(:string)
    end
    opts[flag] = true if flag
    t= @api.send(method, opts).toString
    @result = JSON.parse(t) #@api.send(method, opts).toString)
  rescue
    @error = $!
  end
end

Then /an? (\w+) error should get thrown/ do |error|
  @error.class.to_s.should == 'error'
end

Then /([^\s]+) should be (.+)$/ do |key, value|
  raise @error if @error
  @result.collect do |o|
    o[key].to_s
  end.join(',').should == value
end

Then /([^\s]+) should start with ([^\s]+)/ do |key, value|
  raise @error if @error
  v = key.split('.').inject(@result[0]){|o,c| o[c]}.to_s
  v.to_s.should match(/^#{value}/)
end
