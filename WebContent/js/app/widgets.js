var snewWeather = {

	create : function(name, url) {
		this.name = name;
		this.url = url;
		this.weatherData = null;

		this.loadData = function() {
			var me = this;
			$.ajax({
				method : "GET",
				url : "api/weather/forecast",
				async : false
			}).done(function(resp) {
				if(resp) {
					me.weatherData = resp.data;
					me.place = resp.place;
				}
			}).fail(function() {
				// do nothing
			});
/*			var resp = JSON
					.parse('{"data":[{"day":"Mon","summary":"Foggy in the morning.","temperatureMax":"37.17","icon":"fog","temperatureMin":"24.62","windBearing":"162","pressure":"1008.02","time":"31-03-2016 18:30:00","windSpeed":"2.36","place":"Texas","iconText":"Partly cloudy day"},{"day":"Tue","summary":"Partly cloudy throughout the day.","temperatureMax":"39.28","icon":"partly-cloudy-day","temperatureMin":"25.71","windBearing":"200","pressure":"1008.31","time":"01-04-2016 18:30:00","windSpeed":"2.81","place":"Texas","iconText":"Partly cloudy day"},{"day":"Wed","summary":"Partly cloudy starting in the afternoon.","temperatureMax":"40.23","icon":"partly-cloudy-night","temperatureMin":"26.22","windBearing":"200","pressure":"1008.79","time":"02-04-2016 18:30:00","windSpeed":"2.95","place":"Texas","iconText":"Partly cloudy day"},{"day":"Thu","summary":"Partly cloudy throughout the day.","temperatureMax":"40.36","icon":"partly-cloudy-day","temperatureMin":"26","windBearing":"195","pressure":"1007.87","time":"03-04-2016 18:30:00","windSpeed":"3.9","place":"Texas","iconText":"Partly cloudy day"},{"day":"Fri","summary":"Partly cloudy until evening.","temperatureMax":"38.78","icon":"partly-cloudy-day","temperatureMin":"27.61","windBearing":"187","pressure":"1007.57","time":"04-04-2016 18:30:00","windSpeed":"3.33","place":"Texas","iconText":"Partly cloudy day"},{"day":"Sat","summary":"Light rain until afternoon.","temperatureMax":"37.71","icon":"rain","temperatureMin":"26.79","windBearing":"164","pressure":"1008.17","time":"05-04-2016 18:30:00","windSpeed":"2.01","place":"Texas","iconText":"Partly cloudy day"},{"day":"Sun","summary":"Light rain until afternoon.","temperatureMax":"37.71","icon":"rain","temperatureMin":"26.79","windBearing":"164","pressure":"1008.17","time":"05-04-2016 18:30:00","windSpeed":"2.01","place":"Texas","iconText":"Partly cloudy day"}]}');*/
			
		}

		this.initialize = function() {
			this.loadData();
			this.drawWidget();
		}

		this.drawWidget = function() {
			if (this.weatherData) {
				var firstDay = this.weatherData[1];
				$("#" + this.name + "-firstDayTemp").html(
						"<b>" + firstDay.day + "</b>, " + firstDay.time
								+ " <span>F</span> <span><b>C</b></span>");
				$("#" + this.name + "-firstDayWeatherIcon").html("");
				var firstDayCanvas = document.createElement('canvas');
				firstDayCanvas.setAttribute('width', 84);
				firstDayCanvas.setAttribute('height', 84);
				firstDayCanvas.setAttribute('id', 'firstDaySkyCon');
				$("#" + this.name + "-firstDayWeatherIcon").get()[0]
						.appendChild(firstDayCanvas);
				if (typeof G_vmlCanvasManager != 'undefined') {
					firstDayCanvas = G_vmlCanvasManager
							.initElement(firstDayCanvas);
				}
				var skycons = new Skycons({
					"color" : "black"
				});
				skycons.add("firstDaySkyCon", firstDay.icon);
				skycons.play();

				$("#" + this.name + "-firstDayPlace").text(this.place);
				$("#" + this.name + "-firstDayIconText")
						.text(firstDay.iconText);
				$("#" + this.name + "-firstDayDegrees").text(
						firstDay.temperatureMax);

				var weatherDaysObj = $("#nashville-weather-weatherDays");
				weatherDaysObj.html("");
				var k;
				for (k = 2; ((k < this.weatherData.length)); k++) {
					var dayWeatherDtls = this.weatherData[k];
					var dayWeatherHtml = '<div class="col-sm-2"><div class="daily-weather"><h2 class="day">'
							+ dayWeatherDtls.day
							+ '</h2><h3 class="degrees">'
							+ dayWeatherDtls.temperatureMax
							+ '</h3><span id="'
							+ this.name
							+ '-day-'
							+ k
							+ '">'
							+ '</span><h5>'
							+ dayWeatherDtls.windSpeed
							+ ' <i>km/h</i></h5></div></div>';
					weatherDaysObj.append(dayWeatherHtml);
					var dayCanvas = document.createElement('canvas');
					dayCanvas.setAttribute('width', 32);
					dayCanvas.setAttribute('height', 32);
					var canvasId = this.name + "-skycon" + k;
					dayCanvas.setAttribute('id', canvasId);
					$("#" + this.name + "-day-" + k).get()[0]
							.appendChild(dayCanvas);
					if (typeof G_vmlCanvasManager != 'undefined') {
						dayCanvas = G_vmlCanvasManager.initElement(dayCanvas);
					}

					var skycons = new Skycons({
						"color" : "black"
					});
					skycons.add(this.name + "-skycon" + k, dayWeatherDtls.icon);
					skycons.play();

				}
			}
		}
		return this;
	}
}

var snewTwitter = {

	create : function(name, url) {
		this.name = name;
		this.url = url;
		this.loadData = function() {
			var me = this;
			$.ajax({
				method : "GET",
				url : "api/twitter/tweets",
				async : false
			}).done(function(resp) {
				if(resp) {
					me.twitterData = resp.data;
				}
			}).fail(function() {
				// do nothing
			});
		}

		this.initialize = function() {
			this.loadData();
			if(this.twitterData) {
				this.drawWidget();	
			}			
		}

		this.drawWidget = function() {
			var tweetBox = $("#" + this.name + "-tweetbox"), i;
			tweetBox.html("");
			for (i = 0; i < this.twitterData.length; i++) {
				var tweetObj = this.twitterData[i];
				var avatarPlaceHolderCls = null;
				var tweetDtlsPlaceHolder = null;
				var avatarImgSrc = null;
				var tweetUserNamePlaceHolderCls = null;
				var tweetPosClass = null;
				var twitterDtls = '';
				if (i % 2 != 0) {
					avatarPlaceHolderCls = " pull-left ";
					tweetDtlsPlaceHolder = " pull-right ";
					avatarImgSrc = "http://placehold.it/50/55C1E7/fff";
					tweetUserNamePlaceHolderCls = "";
					tweetPosClass = " left ";
					twitterDtls = twitterDtls
							+ '<small class="'
							+ tweetDtlsPlaceHolder
							+ ' text-muted"> <i class="fa fa-thumbs-up fa-fw"></i>'
							+ tweetObj.likedBy
							+ ' </small>'
							+ '<small class="'
							+ tweetDtlsPlaceHolder
							+ ' text-muted" style="padding-right: 13px;"> <i class="fa fa-clock-o fa-fw"></i> '
							+ tweetObj.createdTime
							+ ' </small>'
							+ '<small class="'
							+ tweetDtlsPlaceHolder
							+ ' text-muted" style="padding-right: 13px;"> <i class="fa fa-retweet fa-fw"></i> '
							+ tweetObj.retweetCount + ' </small>';
				} else {
					avatarPlaceHolderCls = " pull-right ";
					tweetDtlsPlaceHolder = "";
					avatarImgSrc = "http://placehold.it/50/FA6F57/fff";
					tweetUserNamePlaceHolderCls = " pull-right ";
					tweetPosClass = " right ";
					twitterDtls = twitterDtls
							+ '<small class="'
							+ tweetDtlsPlaceHolder
							+ ' text-muted" style="padding-right: 13px;"> <i class="fa fa-retweet fa-fw"></i> '
							+ tweetObj.retweetCount
							+ ' </small>'
							+ '<small class="'
							+ tweetDtlsPlaceHolder
							+ ' text-muted" style="padding-right: 13px;"> <i class="fa fa-clock-o fa-fw"></i> '
							+ tweetObj.createdTime
							+ ' </small>'
							+ '<small class="'
							+ tweetDtlsPlaceHolder
							+ ' text-muted"> <i class="fa fa-thumbs-up fa-fw"></i>'
							+ tweetObj.likedBy + ' </small>';
				}
				var tweet = '<li class="'
						+ tweetPosClass
						+ ' clearfix"><span class="chat-img'
						+ avatarPlaceHolderCls
						+ ' ">'
						+ '<img src="'
						+ avatarImgSrc
						+ '" alt="User Avatar" class="img-circle" /></span>'
						+ '<div class="chat-body clearfix"><div class="header">'+ twitterDtls
						+ '<strong class="primary-font '
						+ tweetUserNamePlaceHolderCls + '">'
						+ tweetObj.userName + '</strong>' + '</div>' + '<p>'
						+ tweetObj.tweet + '</p>' + '</div></li>';
				tweetBox.append(tweet);
			}
		}
		return this;
	}
}

var snewEvents = {

	create : function(name, url, displayName) {
		this.name = name;
		this.url = url;
		this.displayName = displayName;
		this.dataRendered = false;
		this.loadData = function() {
			var me = this;
			$.ajax({
				method : "GET",
				url : this.url,
				async : false
			}).done(function(resp) {
				if(resp) {
					me.dataRendered = true; 
					me.eventGridData = resp.data;
					
					me.eventBarGraphData = {
						"datasets" : {
							"values" : resp.barGraph.dataSet.values,
							"labels" : resp.barGraph.dataSet.labels,
							"color" : "blue"
						},
						"title" : me.displayName + " Bar Chart",
						"noY" : true,
						"height" : "415px",
						"width" : me.availableWidth + "px",
						"background" : "#FFFFFF",
						"shadowDepth" : "1"
					};
					me.eventPieGraphData = {
						"dataset" : {
							"values" : resp.barGraph.dataSet.values,
							"labels" : resp.barGraph.dataSet.labels,
						},
						"title" : me.displayName + " Pie Chart",
						"height" : "415px",
						"width" : me.availableWidth + "px",
						"background" : "#FFFFFF",
						"shadowDepth" : "1"
					};	
				}				
			}).fail(function() {
				// do nothing
			});

		}

		this.initialize = function() {			
			this.availableWidth = $("#" + this.name + "-header").width();
			this.loadData();
			if(this.dataRendered) {
				this.drawBarGraph();
				this.drawPieGraph();
				this.drawGrid();	
			}			
			var me = this;
			$("button[name='" + this.name + "-header-buttons']").click(
					function(comp) {
						$("div[name='" + me.name + "-component']").hide();
						var eventViewId = this.id.replace("-btn", "");
						$("#" + eventViewId).show();
					});
		}

		this.drawBarGraph = function() {
			MaterialCharts.bar("#" + this.name + "-bargraph", this.eventBarGraphData);
		}

		this.drawPieGraph = function() {
			$("#" + this.name + "-piegraph").html("");
			this.pieGraphObject = MaterialCharts.pie("#" + this.name + "-piegraph", this.eventPieGraphData);
		}

		this.drawGrid = function() {
			var i;
			$("#" + this.name + "-grid-div").height(385);
			$("#" + this.name + "-grid-tbody").html("");
			for (i = 0; i < this.eventGridData.length; i++) {
				var evtObj = this.eventGridData[i];
				var rowObj = '<tr><td>'+evtObj.name+'</td><td>'+evtObj.place+'</td><td>'+evtObj.parsedFromTime+'</td><td>'+evtObj.description+'</td></tr>';
				$("#" + this.name + "-grid-tbody").append(rowObj);
			}
		}

		return this;
	}
}


var snewYoutube = {

	create : function(name, url) {
		this.name = name;
		this.url = url;
		this.loadData = function() {
			var me = this;
			$.ajax({
				method : "GET",
				url : "api/youtube",
				async : false
			}).done(function(resp) {
				me.youtubeData = resp;				
			}).fail(function() {
				// do nothing
			});
		}

		this.initialize = function() {
			this.loadData();
			this.drawWidget();
		}

		this.drawWidget = function() {
			$("#"+this.name+"-panelBody").html("");
			var i;
			var finalObj = "";
			var rowObj = '<div class="row youtubeRow" style="margin-top:20px;">';
			for(i=1;i<=this.youtubeData.length;i++) {
				var _youtubeObj = this.youtubeData[i-1];
				rowObj = rowObj +'<a href="'+_youtubeObj.videoUrl+'"	data-toggle="lightbox" data-gallery="youtubevideos"	class="col-sm-4"  data-toggle="tooltip" title="'+_youtubeObj.title+'">' +
				'<img src="'+_youtubeObj.thumbnailUrl+'"	class="img-responsive">	</a>';
				if(i%3 == 0 || i == (this.youtubeData.length)) {
					finalObj =finalObj+ rowObj +"</div>";
					rowObj = '<div class="row">';
				}
			}
			$("#"+this.name+"-panelBody").html(finalObj);
		}
		return this;
	}
}

var snewGoogleSearch = {

		create : function(name, url) {
			this.name = name;
			this.url = url;
			this.loadData = function() {
				var me = this;
				$.ajax({
					method : "GET",
					url : "api/news",
					async : false
				}).done(function(resp) {
					me.googleSearchData = resp.urlLinks;
					me.totalCount = resp.totalCount;
					me.searchString = resp.searchString;
				}).fail(function() {
					// do nothing
				});
			}

			this.initialize = function() {
				this.loadData();
				this.drawWidget();
			}

			this.drawWidget = function() {
				var i;
				$("#"+this.name+"-total").html(this.totalCount);
				$("#"+this.name+"-string").html(this.searchString);
				$("#"+this.name+"-grid-tbody").html("");
				for(i=0;i<this.googleSearchData.length;i++) {
					var rowObj = '<tr><td>'+this.googleSearchData[i]+'</td></tr>';					
					$("#"+this.name+"-grid-tbody").append(rowObj);
				}
			}
		}
}