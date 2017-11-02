<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html manifest="">
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" /> 
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">

    <title>app</title>
    <script type="text/javascript">
        var Ext = Ext || {}; // Ext namespace won't be defined yet...
        // This function is called by the Microloader after it has performed basic
        // device detection. The results are provided in the "tags" object. You can
        // use these tags here or even add custom tags. These can be used by platform
        // filters in your manifest or by platformConfig expressions in your app.
        //
        Ext.beforeLoad = function (tags) {
            var s = location.search,  // the query string (ex "?foo=1&bar")
                profile;

            // For testing look for "?classic" or "?modern" in the URL to override
            // device detection default.
            //
            if (s.match(/\bclassic\b/)) {
                profile = 'classic';
            }
            else if (s.match(/\bmodern\b/)) {
                profile = 'modern';
            }
            else {
                profile = tags.desktop ? 'classic' : 'modern';
                //profile = tags.phone ? 'modern' : 'classic';
            }
            profile = 'modern';
            Ext.manifest = profile; // this name must match a build profile name
            // This function is called once the manifest is available but before
            // any data is pulled from it.
            //
            //return function (manifest) {
                // peek at / modify the manifest object
            //};
        };
    </script>
    
    
    <!-- The line below must be kept intact for Sencha Cmd to build your application -->
   <!--   <script id="microloader" data-app="2f6bf41f-f7ad-4e26-ab91-041b7bb8a461" type="text/javascript" src="bootstrap.js"></script> -->
	
	<!-- 云朵效果 -->
		<!--  <link rel="stylesheet" href="http://dreamsky.github.io/main/blog/common/init.css"> -->
		<style type="text/css">
			a {
				color:#0078ff;
			}
			
			#login{   
			    position: absolute;   
			    width: 300px;   
			    height: 300px;   
			}   
			
			input{   
			    width: 278px;   
			    display: block;
			    height: 18px;   
			    margin-bottom: 10px;   
			    outline: none;   
			    padding: 10px;   
			    font-size: 13px;   
			    color: #000;   
			   <!-- text-shadow:1px 1px 1px; -->   
			    border-top: 1px solid #312E3D;   
			    border-left: 1px solid #312E3D;   
			    border-right: 1px solid #312E3D;   
			    border-bottom: 1px solid #56536A;   
			    border-radius: 4px;   
			    background-color: #fff;   
			}   
			.but{   
			    width: 300px;   
			    display: block; 
			    margin-bottom: 10px;  
			    min-height: 20px;   
			    background-color: #4a77d4;   
			    border: 1px solid #3762bc;   
			    color: #fff;   
			    padding: 9px 14px;   
			    font-size: 15px;   
			    line-height: normal;   
			    border-radius: 5px;   
			    margin: 10 0 0 0 ;   
			}  
		</style>
</head>
<body>
		<script type="text/javascript" src="static/js/assets/three.min.js"></script>
		<script type="text/javascript" src="static/js/assets/Detector.js"></script>
		<script id="vs" type="x-shader/x-vertex">

			varying vec2 vUv;

			void main() {

				vUv = uv;
				gl_Position = projectionMatrix * modelViewMatrix * vec4( position, 1.0 );

			}

		</script>

		<script id="fs" type="x-shader/x-fragment">

			uniform sampler2D map;

			uniform vec3 fogColor;
			uniform float fogNear;
			uniform float fogFar;

			varying vec2 vUv;

			void main() {

				float depth = gl_FragCoord.z / gl_FragCoord.w;
				float fogFactor = smoothstep( fogNear, fogFar, depth );

				gl_FragColor = texture2D( map, vUv );
				gl_FragColor.w *= pow( gl_FragCoord.z, 20.0 );
				gl_FragColor = mix( gl_FragColor, vec4( fogColor, gl_FragColor.w ), fogFactor );

			}

		</script>

		<script type="text/javascript">

			if ( ! Detector.webgl ) Detector.addGetWebGLMessage();

			var container;
			var camera, scene, renderer;
			var mesh, geometry, material;

			var mouseX = 0, mouseY = 0;
			var start_time = Date.now();

			var windowHalfX = window.innerWidth / 2;
			var windowHalfY = window.innerHeight / 2;

			init();

			function init() {

				container = document.createElement( 'div' );
				document.body.appendChild( container );

				// Bg gradient

				var canvas = document.createElement( 'canvas' );
				canvas.width = 32;
				canvas.height = window.innerHeight;

				var context = canvas.getContext( '2d' );

				var gradient = context.createLinearGradient( 0, 0, 0, canvas.height );
				gradient.addColorStop(0, "#1e4877");
				gradient.addColorStop(0.5, "#4584b4");

				context.fillStyle = gradient;
				context.fillRect(0, 0, canvas.width, canvas.height);

				container.style.background = 'url(' + canvas.toDataURL('image/png') + ')';
				container.style.backgroundSize = '32px 100%';

				//

				camera = new THREE.PerspectiveCamera( 30, window.innerWidth / window.innerHeight, 1, 3000 );
				camera.position.z = 6000;

				scene = new THREE.Scene();

				geometry = new THREE.Geometry();

				var texture = THREE.ImageUtils.loadTexture( 'static/image/cloud10.png', null, animate );
				texture.magFilter = THREE.LinearMipMapLinearFilter;
				texture.minFilter = THREE.LinearMipMapLinearFilter;

				var fog = new THREE.Fog( 0x4584b4, - 100, 3000 );

				material = new THREE.ShaderMaterial( {

					uniforms: {

						"map": { type: "t", value: texture },
						"fogColor" : { type: "c", value: fog.color },
						"fogNear" : { type: "f", value: fog.near },
						"fogFar" : { type: "f", value: fog.far },

					},
					vertexShader: document.getElementById( 'vs' ).textContent,
					fragmentShader: document.getElementById( 'fs' ).textContent,
					depthWrite: false,
					depthTest: false,
					transparent: true

				} );

				var plane = new THREE.Mesh( new THREE.PlaneGeometry( 64, 64 ) );

				for ( var i = 0; i < 8000; i++ ) {

					plane.position.x = Math.random() * 1000 - 500;
					plane.position.y = - Math.random() * Math.random() * 200 - 15;
					plane.position.z = i;
					plane.rotation.z = Math.random() * Math.PI;
					plane.scale.x = plane.scale.y = Math.random() * Math.random() * 1.5 + 0.5;

					THREE.GeometryUtils.merge( geometry, plane );

				}

				mesh = new THREE.Mesh( geometry, material );
				scene.add( mesh );

				mesh = new THREE.Mesh( geometry, material );
				mesh.position.z = - 8000;
				scene.add( mesh );

				renderer = new THREE.WebGLRenderer( { antialias: false } );
				renderer.setSize( window.innerWidth, window.innerHeight );
				container.appendChild( renderer.domElement );

				document.addEventListener( 'mousemove', onDocumentMouseMove, false );
				window.addEventListener( 'resize', onWindowResize, false );

			}

			function onDocumentMouseMove( event ) {

				mouseX = ( event.clientX - windowHalfX ) * 0.25;
				mouseY = ( event.clientY - windowHalfY ) * 0.15;

			}

			function onWindowResize( event ) {

				camera.aspect = window.innerWidth / window.innerHeight;
				camera.updateProjectionMatrix();

				renderer.setSize( window.innerWidth, window.innerHeight );

			}

			function animate() {

				requestAnimationFrame( animate );

				position = ( ( Date.now() - start_time ) * 0.03 ) % 8000;

				camera.position.x += ( mouseX - camera.position.x ) * 0.01;
				camera.position.y += ( - mouseY - camera.position.y ) * 0.01;
				camera.position.z = - position + 8000;

				renderer.render( scene, camera );

			}

		</script>
		 <div id="loginDiv" style="float:left;position: absolute;z-index:4;">  
        <form action="sys/login" method="post" id="formLogin">  
            <input type="text" required="required" placeholder="账号" name="account"></input>  
            <input type="password" required="required" placeholder="密码" name="password"></input>  
            <button class="but block" type="submit">登录</button>
            <button style="float:left;" class="but  block" type="reset">重置</button>  
        </form>  
    	</div>  
		<!--  <div id="loginDiv" style="width:300px;float:left;position: absolute;z-index:4;">用户名<input/></div> -->
		<script type="text/javascript">
		var w=screen.width;
		var h=screen.height;
		document.getElementById("loginDiv").style.top = (h/2-100)+"px";
		document.getElementById("loginDiv").style.left = ((w-300)/2)+"px";
		</script>
		
		<script src="./static/js/jquery-1.11.0.min.js"></script>
		<script src="./static/js/jquery.form.js"></script>
		<script type="text/javascript">
		$(document).ready(function() {  
		       $("#formLogin").ajaxForm(function(data){
		    	   if(data != null && data != ''){
		    		   alert(data);  
		    	   }else{
		    		   window.location.href = "sys/toHome";
		    	   }
		       });            
		});  
		</script>
		<!-- <script src="http://dreamsky.github.io/main/blog/common/init.js"></script> -->
</body>
</html>
