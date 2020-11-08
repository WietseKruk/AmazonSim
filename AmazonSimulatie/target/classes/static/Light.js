

function ambientLight(scene){
    var light = new THREE.AmbientLight(0x404040);
    light.intensity = 1;
    scene.add(light);
}



function spotLight(scene){

    var color = 0xffffff;
	var intensity = 0.125;

	var light = new THREE.SpotLight( 0xffffff, 1, 100, Math.PI / 6, 1);
	light.position.set(0 , 16 , 10);
	light.target.position.x = 10;
	light.target.position.y = 0;
	light.target.position.z = 10;
	light.castShadow = true;
	
	var light2 = new THREE.SpotLight( 0xffffff, 1, 100, Math.PI / 6, 1);
	light2.position.set(20 , 16 , 10);
	light2.target.position.x = 20;
	light2.target.position.y = 0;
	light2.target.position.z = 10;
	light2.castShadow = true;

	var light3 = new THREE.SpotLight( 0xffffff, 1, 100, Math.PI / 6, 1);
	light3.position.set(0 , 16 , 20);
	light3.target.position.x = 10;
	light3.target.position.y = 0;
	light3.target.position.z = 20;
	light3.castShadow = true;

	var light4 = new THREE.SpotLight( 0xffffff, 1, 100, Math.PI / 6, 1);
	light4.position.set(20 , 16 , 20);
	light4.target.position.x = 20;
	light4.target.position.y = 0;
	light4.target.position.z = 20;
    light4.castShadow = true;
    
    scene.add ( light, light2, light3, light4);
    scene.add ( light.target, light2.target, light3.target, light4.target);
}

function addLightSource(scene){

    const geometry = new THREE.SphereGeometry( 0.5, 3.2, 3.2 );
    const material = new THREE.MeshBasicMaterial( {color: 0xffd087} );
    const sphere = new THREE.Mesh( geometry, material );
    sphere.position.x = 10;
    sphere.position.y = 16.5;
    sphere.position.z = 15;
    scene.add( sphere );
}
