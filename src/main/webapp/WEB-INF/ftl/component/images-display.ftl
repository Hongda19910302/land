<#--图片展示组件-->
<div class="galleria">
<#list paths as path>
    <img src="temp/${path}">
</#list>
</div>

<script type="text/javascript">
    Galleria.run(".galleria",{
        theme:'classic',
        height:560,
        width:790
    });
</script>