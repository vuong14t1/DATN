
    var chart_config = {
        "chart": {
            "container": "#collapsable-example",

            "animateOnInit": true,
            
            "node": {
                "collapsable": true
            },
            "animation": {
                "nodeAnimation": "easeOutBounce",
                "nodeSpeed": 700,
                "connectorsAnimation": "bounce",
                "connectorsSpeed": 700
            }
        },
        "nodeStructure": {
            "image": "/img/malory.png",
			"HTMLid" : "123",
			"HTMLclass": "hover-me",
            "children": [
                {
                    "image": "/img/lana.png",
                    "collapsed": false,
					"HTMLclass": "hover-me",
					"text": { name: "Parent node" },
                    "children": [
                        {
                            "image": "/img/figgs.png"
                        }
                    ]
                },
                {
                    "image": "/img/sterling.png",
                    "childrenDropLevel": 1,
					"HTMLclass": "hover-me",
                    "children": [
                        {
                           "image": "/img/woodhouse.png",
						   "image": "/img/woodhouse.png"
                        }
                    ]
                },
                {
                    "pseudo": true,
					"HTMLclass": "hover-me",
					"childrenDropLevel": 1,
                    "children": [
                        {
                            "image":"/img/cheryl.png",
							
                        },
						{
							 "image":"/img/cheryl.png",
							"innerHTML": "#first-post",
							 "children": [
								{
									"image": "/img/pam.png",
									text: {
										name: "Simple node",
										title: "One of kind",
										desc: "A basic example",
										contact: { 
											val: "contact me",
											href: "http://twitter.com/",
											target: "_self"
										}
									}
								}	
							 ]
						},
                        {
                            "image": "/img/pam.png",
							text: {
								name: "Simple node",
								title: "One of kind",
								desc: "A basic example",
								contact: { 
									val: "contact me",
									href: "http://twitter.com/",
									target: "_self"
								}
							}
                        }
                    ]
                }
            ]
        }
    };
/*

 // Array approach
    var config = {
        container: "#collapsable-example",

        animateOnInit: true,
        
        node: {
            collapsable: true
        },
        animation: {
            nodeAnimation: "easeOutBounce",
            nodeSpeed: 700,
            connectorsAnimation: "bounce",
            connectorsSpeed: 700
        }
    },
    malory = {
        image: "/img/malory.png"
    },

    lana = {
        parent: malory,
        image: "/img/lana.png"
    },

    figgs = {
        parent: lana,
        image: "/img/figgs.png"
    },

    sterling = {
        parent: malory,
        childrenDropLevel: 1,
        image: "/img/sterling.png"
    },

    woodhouse = {
        parent: sterling,
        image: "/img/woodhouse.png"
    },

    pseudo = {
        parent: malory,
        pseudo: true
    },

    cheryl = {
        parent: pseudo,
        image: "/img/cheryl.png"
    },

    pam = {
        parent: pseudo,
        image: "/img/pam.png"
    },

    chart_config = [config, malory, lana, figgs, sterling, woodhouse, pseudo, pam, cheryl];

*/
