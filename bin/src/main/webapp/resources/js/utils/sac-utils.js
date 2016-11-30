$(function(){
	$('#dataResposta').mask('99/99/9999');
	$('.cep').mask('99999-999');
	$('.telefone').mask('(99)99999-9999');
});

$(function () {
	$('#sandbox-data-container .input-group.date').datepicker({
		orientation: "top auto",
		format: "dd/mm/yyyy",
	    language: "pt-BR",
	    autoclose: true,
	    container: ".input-group.date"
	});
});

//Adiciona comportamento responsívo nos datatable do primefaces
$( document ).ready(function() {
	$( ".ui-datatable-tablewrapper" ).addClass( "table-responsive" );
});

jQuery(function($){  
    $("input.data:text").mask("99/99/9999");  
}); 