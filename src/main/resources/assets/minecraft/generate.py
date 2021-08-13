import json


def register(name, block, copy, mapColor=None):
    return f'{name.upper()} = register("{name}", new {block}(Settings.copy({copy}){"" if mapColor is None else ".mapColor(MapColor." + mapColor + ")"}));'


def register_stairs(name, copy, mapColor=None):
    return f'{name.upper()} = register("{name}", StairsBlockFactory.create({copy}.getDefaultState(), Settings.copy({copy}){"" if mapColor is None else ".mapColor(MapColor." + mapColor + ")"}));'


def generate(name, mapColor):
    ret = []
    static = []
    blockitem = []
    _names = []
    ret.append(register(name, "Block", "REPLACEME", mapColor))
    NAME = name.upper()
    static.append(f"public static final Item {NAME};")
    blockitem.append(f"{NAME} = Items.register(NetherBlocks.{NAME}, ItemGroup.BUILDING_BLOCKS);")
    name = name[:-1]
    ret.append(register(name + "_slab", "SlabBlock", NAME))
    ret.append(register(name + "_wall", "WallBlock", NAME))
    ret.append(register(name + "_fence", "FenceBlock", NAME))
    ret.append(register_stairs(name + "_stairs", NAME))
    NAME = NAME[:-1]
    static.append(f"public static final Item {NAME}_SLAB;")
    static.append(f"public static final Item {NAME}_WALL;")
    static.append(f"public static final Item {NAME}_FENCE;")
    static.append(f"public static final Item {NAME}_STAIRS;")
    blockitem.append(
        f"{NAME}_SLAB = Items.register(NetherBlocks.{NAME}_SLAB, ItemGroup.BUILDING_BLOCKS);"
    )
    blockitem.append(
        f"{NAME}_WALL = Items.register(NetherBlocks.{NAME}_WALL, ItemGroup.BUILDING_BLOCKS);"
    )
    blockitem.append(
        f"{NAME}_FENCE = Items.register(NetherBlocks.{NAME}_FENCE, ItemGroup.BUILDING_BLOCKS);"
    )
    blockitem.append(
        f"{NAME}_STAIRS = Items.register(NetherBlocks.{NAME}_STAIRS, ItemGroup.BUILDING_BLOCKS);"
    )
    return (
        static,
        ret,
        blockitem,
        [f"{name}s", f"{name}_slab", f"{name}_wall", f"{name}_fence", f"{name}_stairs"],
    )


with open("blocknames.json", "r") as file:
    names = json.load(file)

sout = []
rout = []
bout = []
nout = []
for name in names:
    s, r, b, n = generate(name, names[name])
    sout += s
    rout += r
    bout += b
    nout += n


def slab(name):
    return name[:-1] + "_slab"


def fence(name):
    return name[:-1] + "_fence"


def stairs(name):
    return name[:-1] + "_stairs"


def wall(name):
    return name[:-1] + "_wall"


def generate_block_json(name):
    return {"variants": {"": {"model": f"extra_things:block/{name}"}}}


def generate_slab_json(name):
    return {
        "variants": {
            "type=bottom": {"model": f"extra_things:block/{slab(name)}"},
            "type=double": {"model": f"extra_things:block/{name}"},
            "type=top": {"model": f"extra_things:block/{slab(name)}_top"},
        }
    }


def generate_fence_json(name):
    return {
        "multipart": [
            {"apply": {"model": f"extra_things:block/{fence(name)}_post"}},
            {
                "when": {"north": "true"},
                "apply": {"model": f"extra_things:block/{fence(name)}_side", "uvlock": True},
            },
            {
                "when": {"east": "true"},
                "apply": {
                    "model": f"extra_things:block/{fence(name)}_side",
                    "y": 90,
                    "uvlock": True,
                },
            },
            {
                "when": {"south": "true"},
                "apply": {
                    "model": f"extra_things:block/{fence(name)}_side",
                    "y": 180,
                    "uvlock": True,
                },
            },
            {
                "when": {"west": "true"},
                "apply": {
                    "model": f"extra_things:block/{fence(name)}_side",
                    "y": 270,
                    "uvlock": True,
                },
            },
        ]
    }


def generate_stairs_json(name):
    return {
        "variants": {
            "facing=east,half=bottom,shape=inner_left": {
                "model": f"extra_things:block/{stairs(name)}_inner",
                "y": 270,
                "uvlock": True,
            },
            "facing=east,half=bottom,shape=inner_right": {
                "model": f"extra_things:block/{stairs(name)}_inner"
            },
            "facing=east,half=bottom,shape=outer_left": {
                "model": f"extra_things:block/{stairs(name)}_outer",
                "y": 270,
                "uvlock": True,
            },
            "facing=east,half=bottom,shape=outer_right": {
                "model": f"extra_things:block/{stairs(name)}_outer"
            },
            "facing=east,half=bottom,shape=straight": {
                "model": f"extra_things:block/{stairs(name)}"
            },
            "facing=east,half=top,shape=inner_left": {
                "model": f"extra_things:block/{stairs(name)}_inner",
                "x": 180,
                "uvlock": True,
            },
            "facing=east,half=top,shape=inner_right": {
                "model": f"extra_things:block/{stairs(name)}_inner",
                "x": 180,
                "y": 90,
                "uvlock": True,
            },
            "facing=east,half=top,shape=outer_left": {
                "model": f"extra_things:block/{stairs(name)}_outer",
                "x": 180,
                "uvlock": True,
            },
            "facing=east,half=top,shape=outer_right": {
                "model": f"extra_things:block/{stairs(name)}_outer",
                "x": 180,
                "y": 90,
                "uvlock": True,
            },
            "facing=east,half=top,shape=straight": {
                "model": f"extra_things:block/{stairs(name)}",
                "x": 180,
                "uvlock": True,
            },
            "facing=north,half=bottom,shape=inner_left": {
                "model": f"extra_things:block/{stairs(name)}_inner",
                "y": 180,
                "uvlock": True,
            },
            "facing=north,half=bottom,shape=inner_right": {
                "model": f"extra_things:block/{stairs(name)}_inner",
                "y": 270,
                "uvlock": True,
            },
            "facing=north,half=bottom,shape=outer_left": {
                "model": f"extra_things:block/{stairs(name)}_outer",
                "y": 180,
                "uvlock": True,
            },
            "facing=north,half=bottom,shape=outer_right": {
                "model": f"extra_things:block/{stairs(name)}_outer",
                "y": 270,
                "uvlock": True,
            },
            "facing=north,half=bottom,shape=straight": {
                "model": f"extra_things:block/{stairs(name)}",
                "y": 270,
                "uvlock": True,
            },
            "facing=north,half=top,shape=inner_left": {
                "model": f"extra_things:block/{stairs(name)}_inner",
                "x": 180,
                "y": 270,
                "uvlock": True,
            },
            "facing=north,half=top,shape=inner_right": {
                "model": f"extra_things:block/{stairs(name)}_inner",
                "x": 180,
                "uvlock": True,
            },
            "facing=north,half=top,shape=outer_left": {
                "model": f"extra_things:block/{stairs(name)}_outer",
                "x": 180,
                "y": 270,
                "uvlock": True,
            },
            "facing=north,half=top,shape=outer_right": {
                "model": f"extra_things:block/{stairs(name)}_outer",
                "x": 180,
                "uvlock": True,
            },
            "facing=north,half=top,shape=straight": {
                "model": f"extra_things:block/{stairs(name)}",
                "x": 180,
                "y": 270,
                "uvlock": True,
            },
            "facing=south,half=bottom,shape=inner_left": {
                "model": f"extra_things:block/{stairs(name)}_inner"
            },
            "facing=south,half=bottom,shape=inner_right": {
                "model": f"extra_things:block/{stairs(name)}_inner",
                "y": 90,
                "uvlock": True,
            },
            "facing=south,half=bottom,shape=outer_left": {
                "model": f"extra_things:block/{stairs(name)}_outer"
            },
            "facing=south,half=bottom,shape=outer_right": {
                "model": f"extra_things:block/{stairs(name)}_outer",
                "y": 90,
                "uvlock": True,
            },
            "facing=south,half=bottom,shape=straight": {
                "model": f"extra_things:block/{stairs(name)}",
                "y": 90,
                "uvlock": True,
            },
            "facing=south,half=top,shape=inner_left": {
                "model": f"extra_things:block/{stairs(name)}_inner",
                "x": 180,
                "y": 90,
                "uvlock": True,
            },
            "facing=south,half=top,shape=inner_right": {
                "model": f"extra_things:block/{stairs(name)}_inner",
                "x": 180,
                "y": 180,
                "uvlock": True,
            },
            "facing=south,half=top,shape=outer_left": {
                "model": f"extra_things:block/{stairs(name)}_outer",
                "x": 180,
                "y": 90,
                "uvlock": True,
            },
            "facing=south,half=top,shape=outer_right": {
                "model": f"extra_things:block/{stairs(name)}_outer",
                "x": 180,
                "y": 180,
                "uvlock": True,
            },
            "facing=south,half=top,shape=straight": {
                "model": f"extra_things:block/{stairs(name)}",
                "x": 180,
                "y": 90,
                "uvlock": True,
            },
            "facing=west,half=bottom,shape=inner_left": {
                "model": f"extra_things:block/{stairs(name)}_inner",
                "y": 90,
                "uvlock": True,
            },
            "facing=west,half=bottom,shape=inner_right": {
                "model": f"extra_things:block/{stairs(name)}_inner",
                "y": 180,
                "uvlock": True,
            },
            "facing=west,half=bottom,shape=outer_left": {
                "model": f"extra_things:block/{stairs(name)}_outer",
                "y": 90,
                "uvlock": True,
            },
            "facing=west,half=bottom,shape=outer_right": {
                "model": f"extra_things:block/{stairs(name)}_outer",
                "y": 180,
                "uvlock": True,
            },
            "facing=west,half=bottom,shape=straight": {
                "model": f"extra_things:block/{stairs(name)}",
                "y": 180,
                "uvlock": True,
            },
            "facing=west,half=top,shape=inner_left": {
                "model": f"extra_things:block/{stairs(name)}_inner",
                "x": 180,
                "y": 180,
                "uvlock": True,
            },
            "facing=west,half=top,shape=inner_right": {
                "model": f"extra_things:block/{stairs(name)}_inner",
                "x": 180,
                "y": 270,
                "uvlock": True,
            },
            "facing=west,half=top,shape=outer_left": {
                "model": f"extra_things:block/{stairs(name)}_outer",
                "x": 180,
                "y": 180,
                "uvlock": True,
            },
            "facing=west,half=top,shape=outer_right": {
                "model": f"extra_things:block/{stairs(name)}_outer",
                "x": 180,
                "y": 270,
                "uvlock": True,
            },
            "facing=west,half=top,shape=straight": {
                "model": f"extra_things:block/{stairs(name)}",
                "x": 180,
                "y": 180,
                "uvlock": True,
            },
        }
    }


def generate_wall_json(name):
    return {
        "multipart": [
            {"when": {"up": "true"}, "apply": {"model": f"extra_things:block/{wall(name)}_post"}},
            {
                "when": {"north": "low"},
                "apply": {"model": f"extra_things:block/{wall(name)}_side", "uvlock": True},
            },
            {
                "when": {"east": "low"},
                "apply": {
                    "model": f"extra_things:block/{wall(name)}_side",
                    "y": 90,
                    "uvlock": True,
                },
            },
            {
                "when": {"south": "low"},
                "apply": {
                    "model": f"extra_things:block/{wall(name)}_side",
                    "y": 180,
                    "uvlock": True,
                },
            },
            {
                "when": {"west": "low"},
                "apply": {
                    "model": f"extra_things:block/{wall(name)}_side",
                    "y": 270,
                    "uvlock": True,
                },
            },
            {
                "when": {"north": "tall"},
                "apply": {"model": f"extra_things:block/{wall(name)}_side_tall", "uvlock": True},
            },
            {
                "when": {"east": "tall"},
                "apply": {
                    "model": f"extra_things:block/{wall(name)}_side_tall",
                    "y": 90,
                    "uvlock": True,
                },
            },
            {
                "when": {"south": "tall"},
                "apply": {
                    "model": f"extra_things:block/{wall(name)}_side_tall",
                    "y": 180,
                    "uvlock": True,
                },
            },
            {
                "when": {"west": "tall"},
                "apply": {
                    "model": f"extra_things:block/{wall(name)}_side_tall",
                    "y": 270,
                    "uvlock": True,
                },
            },
        ]
    }


def generate_block_model_json(name):
    return {
        "parent": "minecraft:block/cube_all",
        "textures": {"all": f"extra_things:block/{name}"},
    }


def generate_wall_side_model_json(name):
    return {
        "parent": "minecraft:block/template_wall_side",
        "textures": {"wall": f"extra_things:block/{name}"},
    }


def generate_wall_side_tall_model_json(name):
    return {
        "parent": "minecraft:block/template_wall_side_tall",
        "textures": {"wall": f"extra_things:block/{name}"},
    }


def generate_wall_post_model_json(name):
    return {
        "parent": "minecraft:block/template_wall_post",
        "textures": {"wall": f"extra_things:block/{name}"},
    }


def generate_wall_inventory_model_json(name):
    return {
        "parent": "minecraft:block/wall_inventory",
        "textures": {"wall": f"extra_things:block/{name}"},
    }


def generate_stairs_model_json(name):
    return {
        "parent": "minecraft:block/stairs",
        "textures": {
            "bottom": f"extra_things:block/{name}",
            "top": f"extra_things:block/{name}",
            "side": f"extra_things:block/{name}",
        },
    }


def generate_stairs_outer_model_json(name):
    return {
        "parent": "minecraft:block/outer_stairs",
        "textures": {
            "bottom": f"extra_things:block/{name}",
            "top": f"extra_things:block/{name}",
            "side": f"extra_things:block/{name}",
        },
    }


def generate_stairs_inner_model_json(name):
    return {
        "parent": "minecraft:block/inner_stairs",
        "textures": {
            "bottom": f"extra_things:block/{name}",
            "top": f"extra_things:block/{name}",
            "side": f"extra_things:block/{name}",
        },
    }


def generate_slab_model_json(name):
    return {
        "parent": "minecraft:block/slab",
        "textures": {
            "bottom": f"extra_things:block/{name}",
            "top": f"extra_things:block/{name}",
            "side": f"extra_things:block/{name}",
        },
    }


def generate_slab_top_model_json(name):
    return {
        "parent": "minecraft:block/slab_top",
        "textures": {
            "bottom": f"extra_things:block/{name}",
            "top": f"extra_things:block/{name}",
            "side": f"extra_things:block/{name}",
        },
    }


def generate_fence_side_model_json(name):
    return {
        "parent": "minecraft:block/fence_side",
        "textures": {"texture": f"extra_things:block/{name}"},
    }


def generate_fence_post_model_json(name):
    return {
        "parent": "minecraft:block/fence_post",
        "textures": {"texture": f"extra_things:block/{name}"},
    }


def generate_fence_inventory_model_json(name):
    return {
        "parent": "minecraft:block/fence_inventory",
        "textures": {"texture": f"extra_things:block/{name}"},
    }


def generate_parent_model_json(name, extra):
    return {"parent": f"extra_things:block/{name}{extra}"}


for name in names:
    with open(f"../extra_things/models/item/{name}.json", "w") as file:
        json.dump(generate_parent_model_json(name, ""), file, indent=2)
    with open(f"../extra_things/models/item/{fence(name)}.json", "w") as file:
        json.dump(generate_parent_model_json(fence(name), "_inventory"), file, indent=2)
    with open(f"../extra_things/models/item/{wall(name)}.json", "w") as file:
        json.dump(generate_parent_model_json(wall(name), "_inventory"), file, indent=2)
    with open(f"../extra_things/models/item/{stairs(name)}.json", "w") as file:
        json.dump(generate_parent_model_json(stairs(name), ""), file, indent=2)
    with open(f"../extra_things/models/item/{slab(name)}.json", "w") as file:
        json.dump(generate_parent_model_json(slab(name), ""), file, indent=2)

    # with open(f"../extra_things/blockstates/{name}.json", "w") as file:
    #     json.dump(generate_block_json(name), file, indent=2)
    # with open(f"../extra_things/blockstates/{slab(name)}.json", "w") as file:
    #     json.dump(generate_slab_json(name), file, indent=2)
    # with open(f"../extra_things/blockstates/{fence(name)}.json", "w") as file:
    #     json.dump(generate_fence_json(name), file, indent=2)
    # with open(f"../extra_things/blockstates/{stairs(name)}.json", "w") as file:
    #     json.dump(generate_stairs_json(name), file, indent=2)
    # with open(f"../extra_things/blockstates/{wall(name)}.json", "w") as file:
    #     json.dump(generate_wall_json(name), file, indent=2)
    # with open(f"../extra_things/models/block/{name}.json", "w") as file:
    #     json.dump(generate_block_model_json(name), file, indent=2)
    # with open(f"../extra_things/models/block/{wall(name)}_side.json", "w") as file:
    #     json.dump(generate_wall_side_model_json(name), file, indent=2)
    # with open(f"../extra_things/models/block/{wall(name)}_side_tall.json", "w") as file:
    #     json.dump(generate_wall_side_tall_model_json(name), file, indent=2)
    # with open(f"../extra_things/models/block/{wall(name)}_post.json", "w") as file:
    #     json.dump(generate_wall_post_model_json(name), file, indent=2)
    # with open(f"../extra_things/models/block/{wall(name)}_inventory.json", "w") as file:
    #     json.dump(generate_wall_inventory_model_json(name), file, indent=2)
    # with open(f"../extra_things/models/block/{stairs(name)}.json", "w") as file:
    #     json.dump(generate_stairs_model_json(name), file, indent=2)
    # with open(f"../extra_things/models/block/{stairs(name)}_inner.json", "w") as file:
    #     json.dump(generate_stairs_inner_model_json(name), file, indent=2)
    # with open(f"../extra_things/models/block/{stairs(name)}_outer.json", "w") as file:
    #     json.dump(generate_stairs_outer_model_json(name), file, indent=2)
    # with open(f"../extra_things/models/block/{slab(name)}.json", "w") as file:
    #     json.dump(generate_slab_model_json(name), file, indent=2)
    # with open(f"../extra_things/models/block/{slab(name)}_top.json", "w") as file:
    #     json.dump(generate_slab_top_model_json(name), file, indent=2)
    # with open(f"../extra_things/models/block/{fence(name)}_side.json", "w") as file:
    #     json.dump(generate_fence_side_model_json(name), file, indent=2)
    # with open(f"../extra_things/models/block/{fence(name)}_post.json", "w") as file:
    #     json.dump(generate_fence_post_model_json(name), file, indent=2)
    # with open(f"../extra_things/models/block/{fence(name)}_inventory.json", "w") as file:
    #     json.dump(generate_fence_inventory_model_json(name), file, indent=2)
