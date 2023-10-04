# How To USe

## Script Commands

    load [file path] [image name]

    *load command must be called before save*
    save [file path] [image name]


    *load command must be called before brighten*
    brighten [increment (a possitive integer)] [image name] [new image name]

    *load command must be called before darken*
    darken [increment (a positie] [image name] [new image name]

    *load command must be called before vertical-flip*
    vertical-flip [image name] [new image name]

    *load command must be called before horizontal-flip*
    horizontal-flip [image name] [new image name]

    *load command must be called before red-component*
    red-component [image name] [new image name]

    *load command must be called before green-component*
    green-component [image name] [new image name]

    *load command must be called before blue-component*
    blue-component [image name] [new image name]

    *load command must be called before value*
    value-component [image name] [new image name]

    *load command must be called before intensity-component*
    intensity-component [image name] [new image name]

    *load command must be called before luma-component*
    luma-component [image name] [new image name]

    *load command must be called before sharpen*
    sharpen [image name] [new image name]

    *load command must be called before blur*
    blur [image name] [new image name]

    *load command must be called before greyscale*
    greyscale [image name] [new image name]

    *load command must be called before sepia*
    sepia [image name] [new image name]


## Examples

    load res/res2elephant/elephant.ppm elephant
    sharpen elephant elephant-sharpen
    save res/res2elephant/elephant.ppm elephant-sharpen

    load res/res2elephant/elephant.ppm elephant
    brighten elephant elephant-brighten
    save res/res2elephant/elephant.ppm elephant-brighten

    load res/res2elephant/elephant.ppm elephant
    sepia elephant elephant-sepia
    save res/res2elephant/elephant.ppm elephant-sepia





    