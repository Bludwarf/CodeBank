function A(a){
  this.varA = a; // public property
  let varB = b; // private property
  
  // MLV
  Object.defineProperties(A.prototype, {
    faireQuelqueChose : { 
      value: function() {
	    return "A.faireQuelqueChose";
      },
      enumerable: true,
      configurable: true, 
      writable: true
    }
  });
}

/*A.prototype = {
  faireQuelqueChose : function(){
    // ...
  }
};*/

function B(a, b){
  A.call(this, a); // super(args);
  this.varB = b; // public property
  
  // (private) member variable : http://www.dailycoding.com/Posts/object_oriented_programming_with_javascript__timer_class.aspx
  var mVar;
  
  // src : https://developer.mozilla.org/fr/docs/Web/JavaScript/Reference/Objets_globaux/Object/defineProperty
  Object.defineProperty(this, "mVar", {
    get: function() {
      console.log("accès !");
      return mVar;
    },
    set: function(value) { 
      mVar = value;
    }
  });
}
// src : https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Operators/this
B.prototype = Object.create(A.prototype, {
  varB : {
    value: null, 
    enumerable: true, 
    configurable: true, 
    writable: true 
  },
  faireQuelqueChose : { 
    value: function(){ // override
	  return "B.faireQuelqueChose";
	  /*console.log('B.faireQuelqueChose call A.faireQuelqueChose');
      A.prototype.faireQuelqueChose.apply(this, arguments);*/
    },
    enumerable: true,
    configurable: true, 
    writable: true
  }
});
B.prototype.constructor = B;
var b = new B();