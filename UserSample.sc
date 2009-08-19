UserSample : Object
{
	classvar dict;
	classvar base = "/Users/patrick/Documents/SuperCollider/samples/";
	
	*new {|path|
		var buf;
		dict.isNil.if {dict = Dictionary.new;};
		Server.default.serverRunning.not.if {"Default Server is not running. (Are you a fucking Ass?)".throw;};
		
		dict.at(path).isNil.if ({
			buf = Buffer.read(Server.default, base ++ path);
			dict.put(path, buf);
		},{
			buf = dict.at(path);
		});
		
		^buf;
	}
	
	*at {|path|
		^dict.at(path);
	}
	
	*list {
		dict.do {|item|
			item.postln;
		}
	}
	
	*free {|path|
		dict.at(path).notNil.if{
			dict.at(path).free;
			dict.removeAt(path);
		};
	}
	
	*freeAll {
		dict.do {|item|
			item.free;
		};
		dict = nil;
	}
}