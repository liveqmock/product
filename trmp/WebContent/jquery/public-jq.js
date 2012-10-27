jQuery(document).ready(function(){
		jQuery("tr").mouseover(function(){
			if(jQuery(this).attr("id")!='first-tr'){
				jQuery(this).css({background:'#ADE5E2'});
			}
		});
		
		jQuery("tr").mouseout(function(){
			if(jQuery(this).attr("id")!='first-tr'){
				jQuery(this).css({background:''});
			}
		});
		
		jQuery("div#top-select div").mouseover(function(){
			jQuery(this).css({border:'solid 1px #76C6CC'});
		});
		
		jQuery("div#top-select div").mouseout(function(){
			jQuery(this).css({border:''});
		});
		
		jQuery("div#top-select div").mouseout(function(){
			jQuery(this).css({cursor:'pointer'});
		});
		
		jQuery(".closeImg").click(function(){
			jQuery.ajax({url:"delImg.",async:false});
		});
		
		jQuery('#ex3a').jqm({
			 /*	 trigger: '#select-condition,#test-conditaion',    */
			    trigger: '#select-condition',
			    overlay: 30, /* 0-100 (int) : 0 is off/transparent, 100 is opaque */
			    overlayClass: 'whiteOverlay'})
			   /* make dialog draggable, assign handle to title */
			  
			  // Close Button Highlighting. IE doesn't support :hover. Surprise?
		jQuery('input.jqmdX')
			  .hover(
			    function(){ jQuery(this).addClass('jqmdXFocus'); }, 
			    function(){ jQuery(this).removeClass('jqmdXFocus'); })
			  .focus( 
			    function(){ this.hideFocus=true; jQuery(this).addClass('jqmdXFocus'); })
			  .blur( 
			    function(){ jQuery(this).removeClass('jqmdXFocus'); 
		});
		jQuery('#regedit').jqm({
			    trigger: '#test-conditaion',
			    overlay: 30, /* 0-100 (int) : 0 is off/transparent, 100 is opaque */
			    overlayClass: 'whiteOverlay'
		});
			   /* make dialog draggable, assign handle to title */
			  
			  // Close Button Highlighting. IE doesn't support :hover. Surprise?
		jQuery('input.jqmdX')
			  .hover(
			    function(){ jQuery(this).addClass('jqmdXFocus'); }, 
			    function(){ jQuery(this).removeClass('jqmdXFocus'); })
			  .focus( 
			    function(){ this.hideFocus=true; jQuery(this).addClass('jqmdXFocus'); })
			  .blur( 
			    function(){ jQuery(this).removeClass('jqmdXFocus'); 
		});


});
function delImg(id,name){
	jQuery.ajax({url:"delImg.?id="+id,async:true,success:function(){
		jQuery("."+name).hide();
	}});
}
