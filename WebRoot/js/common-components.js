var Selector={
	  props: ['selected'],
	  template: '<div class="btn-group right"><button type="button" class="btn btn-default dropdown-toggle"data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">{{selected}}</button> <ul class="dropdown-menu"><slot></slot></ul></div>'
	}

var FormField={
		  props: ['error','labelname'],
		  template:'<div :class="[error?\'has-error\':\'\',\'form-group\',\'form-dorpdown\']"><label>{{labelname}}</label><slot></slot><span class="help-block">{{error}}</span></div>' 
		}
var Feedback={
		  props: ['success','result'],
		  template:'<div v-if="result" :class="[success?\'has-success\':\'has-error\',\'form-group\' ]" align="right"><label class="control-label">{{result}}</label></div>' 
		}