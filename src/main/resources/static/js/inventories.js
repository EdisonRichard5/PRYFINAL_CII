function load(){
	$.ajax({
		url: "/inventario/loadData",
		method : 'GET',
		dataType : 'json',
		contentType : 'application/json',
		success : function(response){
			
			console.log(response);	
			
			var toData = [];
			var toLabels = [];
			var toColors = [];
			
			$.each(response, function(i, item){
				console.log(item);
				toData.push(item.valor);
				toLabels.push(item.llave);
				var color  = getRandomColor();							
				toColors.push(color);
			});
			
			var config = {
					type: 'doughnut',
					data: {
						datasets: [{
							data: toData,
							backgroundColor: toColors
						}],
						labels: toLabels
					},
					options: {
						responsive: true,
						legend: {
							position: 'top',
						},
						title: {
							display: true,
							text: 'Reporte de inventario'
						},
						animation: {
							animateScale: true,
							animateRotate: true
						}
					}
				};
			
			
			
			var ctx = document.getElementById('chart-area').getContext('2d');
			window.myDoughnut = new Chart(ctx, config);
			
			
			
			
		},
		error : function(err){
			console.log(err);
		}		
	});
}
function getRandomColor() {
	  var letters = '0123456789ABCDEF';
	  var color = '#';
	  for (var i = 0; i < 6; i++) {
	    color += letters[Math.floor(Math.random() * 16)];
	  }
	  return color;
	}
$(document).ready(function(){
	load();
});