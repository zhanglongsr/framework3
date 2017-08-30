<!--普通的自定义指令-->
<#macro sayHello person color="black">
	<p><font color="${color}">Hello,${person}!</font></p>
</#macro>

<#macro top>
	<table>
		<tr>
			<td>
				<#nested>
			</td>
		</tr>
	</table>
</#macro>

<@top>this's top part!</@top>
<table>
form>
	<@sayHello person="zhangxinglong"></@sayHello>
</form>
</table>