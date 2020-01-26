var toData1=[0,0,0,0,0,0,0,0,0,0,0,0]
var toData2=[0,0,0,0,0,0,0,0,0,0,0,0]
var toData3=[0,0,0,0,0,0,0,0,0,0,0,0]
function ordenar(va){
	switch (va) {
	  case 'January':
		  return 0;				    
		  break;
	  case 'February':
		  return 1;					    
		  break;
	  case 'March':
		  return 2;			    
		  break;
	  case 'April':
		  return 3;				    
		  break;
	  case 'May':
		  return 4;				    
		  break;
	  case 'June':
		  return 5;				    
		  break;
	  case 'July':
		  return 6;				    
		  break;
	  case 'August':
		  return 7;				    
		  break;
	  case 'September':
		  return 8;				    
		  break;
	  case 'October':
		  return 9;				    
		  break;
	  case 'November':
		  return 10;				    
		  break;
	  case 'December':
		  return 11;				    
		  break;
	    
	  default:
	    console.log('esta mal escrito el mes');
	}
};
	
function load() {
	$.ajax({
		url: "/inventario/loadData/1",
		method : 'GET',
		dataType : 'json',
		contentType : 'application/json',
		success : function(response){
			var toLabels = [];
			var num=0;
			$.each(response, function(i, item){
				toLabels.push(item.llave);
				num=ordenar(item.mes);
				toData1[num]=item.valor;
			});
			$.ajax({
				url: "/inventario/loadData/5",
				method : 'GET',
				dataType : 'json',
				contentType : 'application/json',
				success : function(response){
					var toLabels2 = [];
					var num2=0;
					$.each(response, function(i, item){
						toLabels2.push(item.llave);
						num2=ordenar(item.mes);
						toData2[num2]=item.valor;
					});
					$.ajax({
						url: "/inventario/loadData/6",
						method : 'GET',
						dataType : 'json',
						contentType : 'application/json',
						success : function(response){
							
							var toLabels3 = [];
							var num3=0;
							$.each(response, function(i, item){
								toLabels3.push(item.llave);
								num3=ordenar(item.mes);
								toData3[num3]=item.valor;
							});
							console.log(toData1);
							console.log(toData2);
							console.log(toData3);
							var barChartData = {
									labels: ['January', 'February', 'March', 'April', 'May', 'June', 'July','August','September','October','November','December'],
									datasets: [{
										label: toLabels[0],
										backgroundColor: getRandomColor(),
										yAxisID: 'y-axis-1',
										data:toData1
									}, {
										label: toLabels2[0],
										backgroundColor: getRandomColor(),
										yAxisID: 'y-axis-2',
										data:toData2
									}, {
										label: toLabels3[0],
										backgroundColor: getRandomColor(),
										yAxisID: 'y-axis-3',
											data:toData3
										}]

									};
									var ctx = document.getElementById('chart-area').getContext('2d');
									window.myBar = new Chart(ctx, {
										type: 'bar',
									data: barChartData,
									options: {
									responsive: true,
									title: {
										display: true,
										text: 'Reporte por Mes'
									},
									tooltips: {
										mode: 'index',
										intersect: true
									},
									scales: {
										yAxes: [{
											type: 'linear', // only linear but allow scale type registration. This allows extensions to exist solely for log scale for instance
											display: true,
											position: 'left',
											id: 'y-axis-1',
										}, {
											type: 'linear', // only linear but allow scale type registration. This allows extensions to exist solely for log scale for instance
											display: true,
											position: 'right',
											id: 'y-axis-2',
											gridLines: {
												drawOnChartArea: false
											}
										}, {
											type: 'linear', // only linear but allow scale type registration. This allows extensions to exist solely for log scale for instance
											display:false,
											position: 'right',
											id: 'y-axis-3',
													gridLines: {
														drawOnChartArea: false
													}
												}],
											}
										}
									});
							
						},
						error : function(err){
							console.log(err);
						}
						
					});
	
				},
				error : function(err){
					console.log(err);
				}
				
			});
	
			
		},
		error : function(err){
			console.log(err);
		}
		
	});
};

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