# Thready
Thready is a library for creating GUIs using object-oriented components.
Thready aims to provide high flexibility in rendering via a complex rendering pipeline.
Due to this, however, it may be difficult to understand Thready's internal code.
As such, this documentation aims to present an overview of Thready's code architecture.

## Modules
Thready consists of multiple modules, which can be swapped out to provide more flexibility.

The renderer module allows for interacting with a graphical canvas on a screen.
This module can be swapped out to allow for an alternative drawing back-end.

Thready provides components (an "item" that appears on screen) and directives
(which give "hints" about how a component should appear). A directive pool allows
for more flexible management of directives.
The "basic" package provides a variety of components, directives, and directive pools.

A Look-And-Feel is a module that determines how a component actually appears.
It can also determine what advanced features a component might support.

## GUI Architecture
Thready renders GUI components through a pipeline that consists of multiple stages:
The box stage, the render stage, the composite stage, and the paint stage.
Thready also contains a system for event and message passing between components, which can be
used to implement more advanced features such as mouse interaction and copy/paste support.
The results of each stage may be cached so that these stages do not have to re-run every time the
GUI appearance is changed.

### Box Stage
The box stage consists of components creating a box tree.
Each box will be treated as a separate entity in the rest of the pipeline.
This is useful if a component would like to not be displayed, or if it consists of multiple portions.
The box tree can also be adjusted to make future stages less complex.

### Render Stage
The render stage determines the size and positioning of a content, and also handles split-able content
and out-of-flow content.

#### Units
Units and context switches allow for splitting large chunks of content, or for handling out-of-flow content.
They also allow nested boxes to be handled by the same layout manager, with minor visual differences.
For performance reasons, units are created by a unit generator, which allows for more fine-grained control of the size of
a unit so that less unit objects need to be created. There are multiple kinds of units and unit generators.

##### Wrapper Unit
A wrapper unit is intended to provide visual decoration or additional functionality around other units.
Rather than having their own size, they have a size offset which allocates an area of space surrounding another unit.

##### Content Unit
A content unit represents the actual content the user will be focusing on. This unit does have it's own size.