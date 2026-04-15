{
  "startPoint": {
    "x": 56,
    "y": 8,
    "heading": "linear",
    "startDeg": 90,
    "endDeg": 180,
    "locked": false
  },
  "lines": [
    {
      "id": "line-yiuhfk28hxe",
      "name": "Path 1",
      "endPoint": {
        "x": 37.56472261735419,
        "y": 25.14366998577526,
        "heading": "linear",
        "startDeg": 90,
        "endDeg": 180
      },
      "controlPoints": [],
      "color": "#7A676C",
      "locked": false,
      "waitBeforeMs": 0,
      "waitAfterMs": 0,
      "waitBeforeName": "",
      "waitAfterName": ""
    },
    {
      "id": "mnighd3h-hce18z",
      "name": "Path 2",
      "endPoint": {
        "x": 42.081081081081074,
        "y": 59.14224751066856,
        "heading": "tangential",
        "reverse": false
      },
      "controlPoints": [],
      "color": "#587685",
      "waitBeforeMs": 0,
      "waitAfterMs": 0,
      "waitBeforeName": "",
      "waitAfterName": ""
    },
    {
      "id": "mnightun-t09rh3",
      "name": "Path 3",
      "endPoint": {
        "x": 75,
        "y": 119,
        "heading": "constant",
        "reverse": false,
        "degrees": 32
      },
      "controlPoints": [],
      "color": "#8A5778",
      "waitBeforeMs": 0,
      "waitAfterMs": 0,
      "waitBeforeName": "",
      "waitAfterName": ""
    },
    {
      "id": "mnigi57k-04pmpa",
      "name": "Path 4",
      "endPoint": {
        "x": 92,
        "y": 12,
        "heading": "tangential",
        "reverse": true
      },
      "controlPoints": [
        {
          "x": 3.999288762446654,
          "y": 56.12588904694168
        }
      ],
      "color": "#C6DBAD",
      "waitBeforeMs": 0,
      "waitAfterMs": 0,
      "waitBeforeName": "",
      "waitAfterName": ""
    }
  ],
  "shapes": [
    {
      "id": "triangle-1",
      "name": "Red Goal",
      "vertices": [
        {
          "x": 144,
          "y": 70
        },
        {
          "x": 144,
          "y": 144
        },
        {
          "x": 120,
          "y": 144
        },
        {
          "x": 138,
          "y": 119
        },
        {
          "x": 138,
          "y": 70
        }
      ],
      "color": "#dc2626",
      "fillColor": "#ff6b6b"
    },
    {
      "id": "triangle-2",
      "name": "Blue Goal",
      "vertices": [
        {
          "x": 6,
          "y": 119
        },
        {
          "x": 25,
          "y": 144
        },
        {
          "x": 0,
          "y": 144
        },
        {
          "x": 0,
          "y": 70
        },
        {
          "x": 7,
          "y": 70
        }
      ],
      "color": "#2563eb",
      "fillColor": "#60a5fa"
    }
  ],
  "sequence": [
    {
      "kind": "path",
      "lineId": "line-yiuhfk28hxe"
    },
    {
      "kind": "path",
      "lineId": "mnighd3h-hce18z"
    },
    {
      "kind": "path",
      "lineId": "mnightun-t09rh3"
    },
    {
      "kind": "path",
      "lineId": "mnigi57k-04pmpa"
    }
  ],
  "settings": {
    "xVelocity": 75,
    "yVelocity": 65,
    "aVelocity": 3.141592653589793,
    "kFriction": 0.1,
    "rWidth": 16,
    "rHeight": 16,
    "safetyMargin": 1,
    "maxVelocity": 40,
    "maxAcceleration": 30,
    "maxDeceleration": 30,
    "fieldMap": "decode.webp",
    "robotImage": "/robot.png",
    "theme": "auto",
    "showGhostPaths": false,
    "showOnionLayers": false,
    "onionLayerSpacing": 3,
    "onionColor": "#dc2626",
    "onionNextPointOnly": false
  },
  "version": "1.2.1",
  "timestamp": "2026-04-03T05:22:44.980Z"
}